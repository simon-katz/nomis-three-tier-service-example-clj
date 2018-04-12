(ns nomis-three-tier-service-example-clj.system.system
  (:require [fake-external-services.layer-1-wfe.fake-fresh-potatoes-handler
             :as fake-fresh-potatoes-handler]
            [fake-external-services.layer-1-wfe.fake-my-mdb-handler :as fake-my-mdb-handler]
            [nomis-three-tier-service-example-clj.layer-1-wfe.handlers
             :as handlers]
            [nomis-three-tier-service-example-clj.layer-1-wfe.server
             :as server]
            [taoensso.timbre :as timbre]))

(defn make-system [config]
  {:config config})

(defn start [system]
  ;; TODO Use integrant.
  ;; TODO Maybe have a separate thing for starting up the fake services.
  ;;      - And `user/start` and `user/stop` will do both things, and `-main`
  ;;        will do only one.
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
          (stop-server :webserver-info)
          ;; Fake services -- wouldn't have these in a real app.
          (stop-server :fake-fresh-potatoes-webserver-info)
          (stop-server :fake-my-mdb-webserver-info)))))
