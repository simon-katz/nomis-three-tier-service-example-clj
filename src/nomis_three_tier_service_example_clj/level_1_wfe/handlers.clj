(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [yada.yada :as yada]))

;;;; TODO You've grabbed some examples that had no explanations, and you have
;;;; all of the following. Learn about them.
;;;; - `yada/as-resource`
;;;; - `yada/handler`
;;;; - `yada/resource`

(defn make-routes []
  ["/"
   
   [["hello-as-resource"
     (yada/as-resource "Hello World!")]

    [true (yada/as-resource nil)]]])

;;;; TODO Set up some real handler tests.

(def x 42)
