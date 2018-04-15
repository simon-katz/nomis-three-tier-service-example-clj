(ns fake-external-services.system.fake-system
  (:require [fake-external-services.layer-1-wfe.fake-fresh-potatoes-handler
             :as fake-fresh-potatoes-handler]
            [fake-external-services.layer-1-wfe.fake-my-mdb-handler
             :as fake-my-mdb-handler]
            [nomis-movies.layer-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

;;;; ___________________________________________________________________________

;;;; TODO Use integrant.

;;;; ___________________________________________________________________________

(defn ^:private start-fake-fresh-potatoes-webserver [system]
  (timbre/info "Starting fake-fresh-potatoes-webserver")
  (assert (nil? (:fake-fresh-potatoes-webserver-info system)))
  (let [config (:config system)]
    (-> system
        (assoc :fake-fresh-potatoes-webserver-info
               (server/make-server (-> config :fresh-potatoes-service :port)
                                   (fake-fresh-potatoes-handler/make-handler
                                    config))))))

(defn ^:private stop-fake-fresh-potatoes-webserver [system]
  (timbre/info "Stopping fake-fresh-potatoes-webserver")
  (if-let [server-map (:fake-fresh-potatoes-webserver-info system)]
    (do (server/stop-server server-map)
        (dissoc system :fake-fresh-potatoes-webserver-info))
    system))

;;;; ___________________________________________________________________________

(defn ^:private start-fake-my-mdb-webserver [system]  
  (timbre/info "Starting fake-my-mdb-webserver")
  (assert (nil? (:fake-my-mdb-webserver-info system)))
  (let [config (:config system)]
    (-> system
        (assoc :fake-my-mdb-webserver-info
               (server/make-server (-> config :my-mdb-service :port)
                                   (fake-my-mdb-handler/make-handler config))))))

(defn ^:private stop-fake-my-mdb-webserver [system]
  (timbre/info "Stopping fake-my-mdb-webserver")
  (if-let [server-map (:fake-my-mdb-webserver-info system)]
    (do (server/stop-server server-map)
        (dissoc system :fake-my-mdb-webserver-info))
    system))

;;;; ___________________________________________________________________________

(defn start [system]
  (timbre/info "Starting fake system")
  (-> system
      start-fake-fresh-potatoes-webserver
      start-fake-my-mdb-webserver))

(defn stop [system]
  (timbre/info "Stopping fake system")
  (-> system
      stop-fake-fresh-potatoes-webserver
      stop-fake-my-mdb-webserver))
