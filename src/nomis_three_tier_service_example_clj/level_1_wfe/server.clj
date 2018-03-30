(ns nomis-three-tier-service-example-clj.level-1-wfe.server
  (:require [yada.yada :as yada]))

(defn make-server [routes]
  (yada/listener routes
                 {:port 3000}))

(defn stop [server-map]
  ((:close server-map)))
