(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as handlers]
            [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  ;; TODO Use integrant.
  (timbre/info "Starting system")
  (assert (nil? (:server-map system)))
  (let [config     (:config system)
        routes     (handlers/make-routes config)
        server-map (server/make-server config routes)]
    (assoc system :server-map server-map)))

(defn stop [system]
  (timbre/info "Stopping system")
  (if (:server-map system)
    (let [config (:config system)]
      (server/stop config (:server-map system))
      (dissoc system
              :server-map
              :routes))
    system))
