(ns nomis-three-tier-service-example-clj.system.system
  (:require [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as handlers]
            [nomis-three-tier-service-example-clj.level-1-wfe.server :as server]
            [fake-external-services.fake-movie-handler-1 :as fake-movie-handler-1]
            [fake-external-services.fake-movie-handler-2 :as fake-movie-handler-2]
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
                       (handlers/make-handler config))
          ;; Fake services -- wouldn't have these in a real app.
          (make-server :fake-movie-service-1-webserver-info
                       (-> config :movie-service-1 :port)
                       (fake-movie-handler-1/make-handler config))
          (make-server :fake-movie-service-2-webserver-info
                       (-> config :movie-service-2 :port)
                       (fake-movie-handler-2/make-handler config))))))

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
          (stop-server :webserver-info)
          ;; Fake services -- wouldn't have these in a real app.
          (stop-server :fake-movie-service-1-webserver-info)
          (stop-server :fake-movie-service-2-webserver-info)))))
