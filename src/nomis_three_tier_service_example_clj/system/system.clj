(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as handlers]
            [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  (timbre/info "Starting system")
  (assert (nil? (:server-map system)))
  ;; TODO Use integrant.
  (let [ctx        system
        routes     (handlers/make-routes ctx)
        ctx        (assoc ctx :routes routes)
        server-map (server/make-server ctx)
        ctx        (assoc ctx :server-map server-map)]
    ctx))

(defn stop [system]
  (timbre/info "Stopping system")
  (if (:server-map system)
    (do (server/stop (:server-map system))
        (dissoc system
                :server-map
                :routes))
    system))
