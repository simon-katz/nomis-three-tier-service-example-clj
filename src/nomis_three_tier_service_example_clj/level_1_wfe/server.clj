(ns nomis-three-tier-service-example-clj.level-1-wfe.server
  (:require [yada.yada :as yada]))

(defn make-server [config routes]
  (yada/listener routes
                 {:port (:port config)}))

(defn stop [server-map]
  ((:close server-map)))
