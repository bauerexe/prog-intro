"use strict"

const map = {'x': 0, 'y': 1, 'z': 2};
const exprBinary = (func) => (x, y) => (...args) => func(x(...args), y(...args));

const exprUnary = (func) => (x) => (...args) => func(x(...args));

const add = exprBinary((expr1, expr2) => expr1 + expr2);

const subtract = exprBinary((expr1, expr2) => expr1 - expr2);

const multiply = exprBinary((expr1, expr2) => expr1 * expr2);

const divide = exprBinary((expr1, expr2) => expr1 / expr2);

const negate = exprUnary((expr) => -expr);

const cnst = (expr) => () => expr;

const variable = (name) => (...args) => args[map[name]];

const square = exprUnary((expr) => expr ** 2);

const sqrt = exprUnary((expr) => Math.sqrt(Math.abs(expr)));

const pi = cnst(Math.PI);

const e = cnst(Math.E);