(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [yada.yada :as yada]))

(defn make-routes []
  ["/"
   [["hello" (yada/as-resource "Hello World!")]
    ["test" (yada/resource {:produces "text/plain"
                            :response "This is a test!"})]
    [true (yada/as-resource nil)]]])

;;;; TODO Look at some of the following.

(def x 42)


(yada/handler
 {:methods
  {:get
   {:produces "text/html"
    :response "<h1>Hello World!</h1>"}}})


(yada/handler "Hello World!")
