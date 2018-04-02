(ns nomis-three-tier-service-example-clj.level-1-wfe.server
  (:require [yada.yada :as yada]))

(defn make-server [ctx]
  (yada/listener (:routes ctx)
                 {:port (get-in ctx [:config :port])}))

(defn stop [server-map]
  ((:close server-map)))
