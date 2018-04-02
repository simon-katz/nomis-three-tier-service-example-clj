(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as handlers]
            [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  (assert (nil? (:server-map system)))
  (let [routes     (handlers/make-routes)
        server-map (server/make-server routes)]
    (timbre/info "Started system")
    (assoc system :server-map server-map)))

(defn stop [system]
  (if (:server-map system)
    (do (server/stop (:server-map system))
        (timbre/info "Stopped system")
        (dissoc system :server-map))
    system))
