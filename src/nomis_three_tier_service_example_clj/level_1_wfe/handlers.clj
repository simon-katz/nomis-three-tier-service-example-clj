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
    
    ["hello-as-handler"
     (yada/handler "Hello World!")]

    ["some-plain-text"
     (yada/resource {:produces "text/plain"
                     :response "Some plain text"})]
    
    ["an-edn-map-1"
     (yada/handler {:this-will-be :an-edn
                    :map          {:hello "World!"}})]
    
    ["an-edn-map-2"
     (yada/resource {:produces "application/edn"
                     :response {:this-will-be :an-edn
                                :map          {:hello "World!"}}})]
    
    ["a-json-map"
     (yada/resource {:produces "application/json"
                     :response {:this-will-be :a-json
                                :map          {:hello "World!"}}})]
    
    [true (yada/as-resource nil)]]])

;;;; TODO Set up some real handler tests.

(def x 42)
