(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [yada.yada :as yada]))

(defn make-routes []
  ["/"
   [["hello" (yada/as-resource "Hello World!")]
    ["hello2" (yada/handler "Hello World!\n2")]
    ["hello3" (yada/handler
               ;; Example from https://github.com/juxt/yada
               ;; - Bad example?
               ;;   - This is just a map that gets turned into pretty HTML, right?
               {:methods
                {:get
                 {:produces "text/html"
                  :response "<h1>Hello World!</h1>"}}})]
    ["hello4" (yada/handler
               {:a
                {:b
                 {:c "cccc"
                  :d "<h1>Hello World!</h1>"}}})]
    ["test" (yada/resource {:produces "text/plain"
                            :response "This is a test!"})]
    [true (yada/as-resource nil)]]])

;;;; TODO Set up some real handler tests.

(def x 42)
