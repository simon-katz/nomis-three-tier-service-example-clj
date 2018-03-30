(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]))

(defn make-system [conventional-config]
  {})

(defn start [system]
  (assert (nil? (:server-map system)))
  (assoc system :server-map (server/make-server)))

(defn stop [system]
  (if (:server-map system)
    (do (server/stop (:server-map system))
        (dissoc system :server-map))
    system))
