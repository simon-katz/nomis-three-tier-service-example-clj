(ns nomis-three-tier-service-example-clj.level-1-wfe.server
  (:require [ring.adapter.jetty :as jetty]
            [taoensso.timbre :as timbre]))

(defn make-server [config handler]
  (let [port (:port config)]
    (timbre/info "Starting webserver on port" port)
    {:jetty-webserver (jetty/run-jetty handler
                                       {:port  port
                                        :join? false})}))

(defn stop [config server-map]
  (timbre/info "Stopping webserver")
  (.stop (:jetty-webserver server-map)))
