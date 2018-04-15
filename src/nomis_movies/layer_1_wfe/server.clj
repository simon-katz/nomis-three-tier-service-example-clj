(ns nomis-movies.layer-1-wfe.server
  (:require [ring.adapter.jetty :as jetty]
            [taoensso.timbre :as timbre]))

(defn make-server [port handler]
  (timbre/info "Starting webserver on port" port)
  {:port port
   :jetty-webserver (jetty/run-jetty handler
                                     {:port  port
                                      :join? false})})

(defn stop-server [config server-map]
  (timbre/info "Stopping webserver on port" (:port server-map))
  (.stop (:jetty-webserver server-map)))
