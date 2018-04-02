(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [yada.yada :as yada]))

(defn make-routes []
  ["/"
   
   [["hello-as-resource"
     (yada/as-resource "Hello World!")]

    [true (yada/as-resource nil)]]])

;;;; TODO Set up some real handler tests.

(def x 42)
