(defn abs_op [op]
      #(mapv op %1 %2))

(def v+ (abs_op +))
(def v- (abs_op -))
(def v* (abs_op *))
(def vd (abs_op /))

(def m+ (abs_op v+))
(def m- (abs_op v-))
(def m* (abs_op v*))
(def md (abs_op vd))

(def c+ (abs_op m+))
(def c- (abs_op m-))
(def c* (abs_op m*))
(def cd (abs_op md))

(defn v*s [v s] (mapv #(* %1 s) v))
(defn scalar [v1 v2] (apply + (v* v1 v2)))
(defn transpose [m] (apply mapv vector m))
(defn m*v [m v] (mapv #(scalar %1 v) m))
(defn m*m [m1 m2] (mapv #(m*v (transpose m2) %1) m1))
(defn m*s [m s] (mapv #(v*s %1 s) m))
(defn vect [v1 v2]
      [(- (* (v1 1) (v2 2)) (* (v2 1) (v1 2)))
       (- (* (v2 0) (v1 2)) (* (v1 0) (v2 2)))
       (- (* (v1 0) (v2 1)) (* (v2 0) (v1 1)))])