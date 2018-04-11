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
  (let [config     (:config system)
        server-map (server/make-server (:port config)
                                       (handlers/make-handler config))
        ;; Fake services -- wouldn't have these in a real app.
        fake-movie-service-1-server-map (server/make-server (-> config
                                                                :movie-service-1
                                                                :port)
                                                            (fake-movie-handler-1/make-handler config))
        fake-movie-service-2-server-map (server/make-server (-> config
                                                                :movie-service-2
                                                                :port)
                                                            (fake-movie-handler-2/make-handler config))]
    (assoc system
           :webserver-info server-map
           :fake-movie-service-1-webserver-info fake-movie-service-1-server-map
           :fake-movie-service-2-webserver-info fake-movie-service-2-server-map)))

(defn stop [system]
  (timbre/info "Stopping system")
  (if (:webserver-info system)
    (let [config (:config system)]
      (server/stop-server config
                          (:webserver-info system))
      (server/stop-server config
                          (:fake-movie-service-1-webserver-info system))
      (server/stop-server config
                          (:fake-movie-service-2-webserver-info system))
      (dissoc system
              :webserver-info
              :fake-movie-service-1-webserver-info
              :fake-movie-service-2-webserver-info))
    system))
