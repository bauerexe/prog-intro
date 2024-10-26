; Домашнее задание 10. Функциональные выражения на Clojure
(require '[clojure.math :as math])
(require '[clojure.string :as str])

(defn UnaryOperation [op]
  (fn [a]
    (fn [vars]
      (op (a vars)))))

(defn BinaryOperation [op]
  (fn [a b]
    (fn [vars]
      (op (a vars) (b vars)))))

(defn constant [value]
  (fn [vars] value))

(defn variable [name] (fn [map] (get map name)))

(def cos (UnaryOperation math/cos))
(def sin (UnaryOperation math/sin))
(def negate (UnaryOperation -))
(def add (BinaryOperation +))
(def subtract (BinaryOperation -))
(def multiply (BinaryOperation *))
(defn div' [a b] (/ (double a) (double b)))
(def divide (BinaryOperation div'))

(def operations
  {"+" add "-" subtract "*" multiply "/" divide "cos" cos "sin" sin "negate" negate "v" variable "c" constant})

(defn create_expr [expr map-op]
  (cond
    (list? expr) (apply (get map-op (str (first expr))) (map #(create_expr % map-op) (rest expr)))
    (number? expr) ((get map-op "c") expr)
    :else ((get map-op "v") (str expr))))

(defn parseFunction [expr-string]
  (let [parsed (read-string expr-string)]
    (create_expr parsed operations)))
; Домашнее задание 11. Объектные выражения на Clojure
(load-file "proto.clj")
(def ExprProto
  {:evaluate        (fn [_ _])
   :toString        (fn [_])
   :toStringPostfix (fn [_])})

(def evaluate (method :evaluate))

(def toString (method :toString))

(def toStringPostfix (method :toStringPostfix))

(defn Operation [operation op-name]
  (fn [& args]
    (assoc
      {:evaluate        (fn [_ vars] (apply operation (map #(evaluate % vars) args)))
       :toString        (fn [_] (str "(" op-name " " (clojure.string/join " " (map toString args)) ")"))
       :toStringPostfix (fn [_] (str "(" (clojure.string/join " " (map toStringPostfix args)) " " op-name ")"))
       }
      :proto ExprProto)))

(def Add (Operation + "+"))
(def Subtract (Operation - "-"))
(def Multiply (Operation * "*"))
(def Exp (Operation math/exp "exp"))
(def Ln (Operation (fn [x] (math/log x)) "ln"))
(def Negate (Operation (fn [x] (- x)) "negate"))
(def Divide (Operation (fn [x y] (/ (double x) (double y))) "/"))

(defn Variable [name]
  {:name            name
   :evaluate        (fn [_ vars]
                      (get vars (str/lower-case (subs name 0 1))))
   :toStringPostfix (fn [this] (:name this))
   :toString        (fn [this] (:name this))})

(defn Constant [value]
  {:evaluate        (fn [_ _] value)
   :toString        (fn [_] (str value))
   :toStringPostfix (fn [_] (str value))})

(def OperationsObject
  {"+" Add "-" Subtract "*" Multiply "/" Divide "negate" Negate "exp" Exp "ln" Ln "v" Variable "c" Constant})

(defn parseObject [expr-string]
  (let [parsed (read-string expr-string)]
    (create_expr parsed OperationsObject)))
; Домашнее задание 12. Комбинаторные парсеры
(load-file "parser.clj")

(defn *word [word] (apply +seq (reduce #(conj %1 (+char (str %2))) [] word)))

(defn brackets_to_object [mass]
  (apply (get OperationsObject (last mass)) (seq (butlast mass))))

(def parseObjectPostfix
  (let
    [*all-chars (mapv char (range 0 128))
     *constant (+map Constant (+map read-string (+str (+seqf concat (+seq (+opt (+char "-"))) (+plus (+char "1234567890."))))))
     *var (+map Variable (+str (+plus (+char "xXyYzZ"))))
     *parseOp (+or (+str (*word "negate")) (+str (+seq (+char "+-*/"))))
     *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars)))
     *ws (+ignore (+star *space))]
    (letfn [(*seq [parser]
              (+seqn 1 (+char "(") (+or (+seq *ws parser *ws parser *ws *parseOp) (+seq *ws parser *ws *parseOp)) *ws (+char ")")))
            (*brackets [] (+map brackets_to_object (*seq (delay (*ary)))))
            (*ary [] (+or *var *constant (*brackets)))]
      (+parser (+seqn 0 *ws (*ary) *ws)))))
