(ns nomis-movies.system.system
  (:require [nomis-movies.layer-1-wfe.handlers :as handlers]
            [nomis-movies.layer-1-wfe.server :as server]
            [taoensso.timbre :as timbre]))

;;;; ___________________________________________________________________________

;;;; TODO Use integrant.

;;;; ___________________________________________________________________________

(defn ^:private start-webserver [system]
  (timbre/info "Starting webserver")
  (assert (nil? (:webserver-info system)))
  (let [config (:config system)]
    (-> system
        (assoc :webserver-info
               (server/make-server (:port config)
                                   (handlers/make-handler config))))))

(defn ^:private stop-webserver [system]
  (timbre/info "Stopping webserver")
  (if-let [server-map (:webserver-info system)]
    (do (server/stop-server server-map)
        (-> system
            (dissoc :webserver-info)))
    system))

;;;; ___________________________________________________________________________

(defn make-system [config]
  {:config config})

(defn start [system]
  (timbre/info "Starting system")
  (-> system
      start-webserver))

(defn stop [system]
  (timbre/info "Stopping system")
  (-> system
      stop-webserver))
