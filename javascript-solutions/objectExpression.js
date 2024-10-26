"use strict"

const allowedVariables = ['x', 'y', 'z'];


function Expression(funcSign, evalFunc, ...args) {
    this.args = args;
    this.funcSign = funcSign;
    this.evalFunc = evalFunc;
}

Expression.prototype.toString = function () {
    if (this.args.length === 0) {
        return this.funcSign;
    }
    return `${this.args.map(arg => arg.toString()).join(" ")} ${this.funcSign}`;
}

Expression.prototype.prefix = function () {
    if (this.args.length === 0) {
        return this.funcSign;
    }
    return `(${this.funcSign} ${this.args.map(arg => arg.prefix()).join(' ')})`;
}

Expression.prototype.evaluate = function (x, y, z) {
    return this.evalFunc(...this.args.map((item) => item.evaluate(x, y, z)));
}

function createFunc(funcSign, evalFunc) {
    return function (...args) {
        return new Expression(funcSign, evalFunc, ...args);
    };
}

function Const(value) {
    return new Expression(value.toString(), () => value);
}

const map = {x: 0, y: 1, z: 2};

function Variable(name) {
    this.evaluate = (...args) => args[map[name]];
    this.toString = () => name;
    this.prefix = () => name;
}

const meanEval = (...operands) => operands.reduce((acc, value) => acc + value, 0) / operands.length;
const varEval = (...operands) => operands.reduce((acc, value) =>
        acc + Math.pow((value - meanEval(...operands)), 2), 0) /
    operands.length;

const Mean = createFunc('mean', meanEval);
const Var = createFunc('var', varEval);
const Add = createFunc("+", (a, b) => a + b);
const Subtract = createFunc("-", (a, b) => a - b);
const Multiply = createFunc("*", (a, b) => a * b);
const Divide = createFunc("/", (a, b) => a / b);
const Negate = createFunc("negate", a => -a);
const Sin = createFunc("sin", a => Math.sin(a));
const Cos = createFunc("cos", a => Math.cos(a));

const mapOp = {
    "+": [2, Add],
    "-": [2, (...arg) => new Subtract(...arg)],
    "*": [2, (...arg) => new Multiply(...arg)],
    "/": [2, (...arg) => new Divide(...arg)],
    "negate": [1, (...arg) => new Negate(...arg)],
    "cos": [1, (...arg) => new Cos(...arg)],
    "sin": [1, (...arg) => new Sin(...arg)],
    "mean": [0, (...args) => new Mean(...args)],
    "var": [0, (...args) => new Var(...args)]
}


function parse(expression) {
    const stack = [];
    const array = expression.trim().split(/\s+/);//.filter(token => token !== '');
    array.forEach(token => {
        if (token in mapOp) {
            const [arity, operationConstructor] = mapOp[token];
            if (arity === 1) {
                const operand = stack.pop();
                stack.push(operationConstructor(operand));
            } else if (arity === 2) {
                const right = stack.pop();
                const left = stack.pop();
                stack.push(operationConstructor(left, right));
            }
        } else {
            stack.push(Number.isFinite(Number(token)) ? new Const(Number(token)) : new Variable(token));
        }
    });
    return stack.pop();
}

function validateArgs(expected, args, op, pos) {
    if (args.length !== expected) throw new Error(`${op} op at pos ${pos} requires exactly ${expected} args`);
}

function parsePrefix(expression) {
    let noParens = expression.replace(/\(/g, " ( ");
    noParens = noParens.replace(/\)/g, " ) ");
    const tokensRaw = noParens.split(" ");
    const tokens = tokensRaw.filter(token => token !== '');

    function parseToken(tokens, pos = 0) {
        if (pos >= tokens.length) throw new Error(`Unexpected end of expression at pos ${pos}`);
        const token = tokens[pos];
        let nextPos = pos + 1;
        if (token === '(') {
            if (nextPos >= tokens.length) throw new Error("Expected op after '(' at pos " + nextPos);
            const op = tokens[nextPos++];
            let args = [];
            while (tokens[nextPos] !== ')' && nextPos < tokens.length) {
                let result = parseToken(tokens, nextPos);
                args.push(result.expr);
                nextPos = result.nextPos;
            }
            if (nextPos >= tokens.length || tokens[nextPos] !== ')') throw new Error("Expected ')' at pos " + nextPos);
            nextPos++;
            if (mapOp[op]) {
                const [argCount, operation] = mapOp[op];
                if (argCount !== 0) {
                    validateArgs(argCount, args, op, pos);
                }
                return {expr: operation(...args), nextPos: nextPos};
            } else {
                throw new Error(`Unknown operator: ${op} at pos ${pos}`);
            }
        } else if (token === ')') {
            throw new Error("Unexpected ')' at pos " + pos);
        } else {
            let node;
            if (Number.isFinite(Number(token)))
                node = Const(Number(token));
            else if (allowedVariables.includes(token))
                node = new Variable(token);
            else
                throw new Error(`Unknown operator or variable: ${token} at pos ${pos}`);
            return {expr: node, nextPos: nextPos};
        }
    }

    const result = parseToken(tokens);
    if (result.nextPos < tokens.length) throw new Error("Extra tokens after valid expression at position " + result.nextPos);
    return result.expr;
}