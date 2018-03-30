(ns nomis-three-tier-service-example-clj.level-1-wfe.server
  (:require [yada.yada :as y]))

(defn make-server []
  (y/listener
   ["/"
    [["hello" (y/as-resource "Hello World!")]
     ["test" (y/resource {:produces "text/plain"
                          :response "This is a test!"})]
     [true (y/as-resource nil)]]]
   {:port 3000}))

(defn stop [server-map]
  ((:close server-map)))
