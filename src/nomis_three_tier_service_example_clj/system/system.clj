(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as handlers]
            [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  ;; TODO Use integrant.
  (timbre/info "Starting system")
  (assert (nil? (:webserver-info system)))
  (let [config     (:config system)
        handler    (handlers/make-handler config)
        server-map (server/make-server config handler)]
    (assoc system :webserver-info server-map)))

(defn stop [system]
  (timbre/info "Stopping system")
  (if (:webserver-info system)
    (let [config (:config system)]
      (server/stop config (:webserver-info system))
      (dissoc system
              :webserver-info))
    system))
