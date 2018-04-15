(ns fake-external-services.system.system
  (:require
   [fake-external-services.layer-1-wfe.fake-fresh-potatoes-handler
    :as fake-fresh-potatoes-handler]
   [fake-external-services.layer-1-wfe.fake-my-mdb-handler
    :as fake-my-mdb-handler]
   [nomis-movies.layer-1-wfe.server :as server]
   [taoensso.timbre :as timbre]))

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
          (make-server :fake-fresh-potatoes-webserver-info
                       (-> config :fresh-potatoes-service :port)
                       (fake-fresh-potatoes-handler/make-handler config))
          (make-server :fake-my-mdb-webserver-info
                       (-> config :my-mdb-service :port)
                       (fake-my-mdb-handler/make-handler config))))))

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
          (stop-server :fake-fresh-potatoes-webserver-info)
          (stop-server :fake-my-mdb-webserver-info)))))
