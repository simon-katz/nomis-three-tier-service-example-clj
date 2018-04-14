(ns nomis-three-tier-service-example-clj.system.system
  (:require
   [nomis-three-tier-service-example-clj.layer-1-wfe.handlers :as handlers]
   [nomis-three-tier-service-example-clj.layer-1-wfe.server :as server]
   [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  ;; TODO Use integrant.
  (timbre/info "Starting system")
  (assert (nil? (:webserver-info system)))
  (let [config (:config system)]
    (letfn [(make-server [sys system-map-key port handler]
              (assert (nil? (get sys system-map-key)))
              (assoc sys
                     system-map-key
                     (server/make-server port handler)))]
      (-> system
          (make-server :webserver-info
                       (:port config)
                       (handlers/make-handler config))))))

(defn stop [system]
  (timbre/info "Stopping system")
  (let [config (:config system)]
    (letfn [(stop-server [sys system-map-key]
              (if-let [server-map (get sys system-map-key)]
                (do
                  (server/stop-server config server-map)
                  (dissoc sys system-map-key))
                sys))]
      (-> system
          (stop-server :webserver-info)))))
