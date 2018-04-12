(ns user
  (:require [clj-http.client :as http-client]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pp pprint]]
            [clojure.repl :refer :all ; [apropos dir doc find-doc pst source]
             ]
            [clojure.string :as str]
            [clojure.tools.namespace.move :refer :all]
            [clojure.tools.namespace.repl :refer :all]
            [fake-external-services.system.system :as fake-system]
            [midje.repl :refer :all]
            [nomis-three-tier-service-example-clj.layer-2-domain.movies
             :as movies]
            [nomis-three-tier-service-example-clj.system.main :as main]
            [nomis-three-tier-service-example-clj.system.system :as system]))

;;;; ___________________________________________________________________________
;;;; Clojure workflow.
;;;; See:
;;;; - http://nomistech.blogspot.co.uk/2013/06/stuart-sierras-clojure-development_18.html
;;;; - http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded
;;;; - https://github.com/stuartsierra/component#reloading

(defonce fake-services
  nil)

(defn init-fake-services []
  (alter-var-root #'fake-services
                  (fn [_]
                    (fake-system/make-fake-services @#'main/config))))

(defn start-fake-services []
  (alter-var-root #'fake-services fake-system/start))

(defn stop-fake-services []
  (alter-var-root #'fake-services fake-system/stop))

(defonce the-system
  ;; "A container for the current instance of the application.
  ;; Only used for interactive development."
  ;;
  ;; Don't want to lose this value if this file is recompiled (when
  ;; changes are made to the useful additional utilities for the REPL
  ;; at the end of the file), so use `defonce`.
  ;; But note that this /is/ blatted when a `reset` is done.
  nil)

(defn init
  "Creates a system and makes it the current development system."
  []
  (init-fake-services)
  (alter-var-root #'the-system
                  (fn [_]
                    (system/make-system @#'main/config))))

(defn start
  "Starts the current development system."
  []
  (start-fake-services)
  (alter-var-root #'the-system system/start))

(defn stop
  "Shuts down and destroys the current development system."
  []
  (stop-fake-services)
  (alter-var-root #'the-system system/stop))

(defn go
  "Creates a system, makes it the current development system and starts it."
  []
  (init)
  (start))

(defn reset
  "Stop, refresh and go."
  []
  (stop)
  (refresh :after 'user/go))

(defn reset-all
  "Stop, refresh-all and go."
  []
  (stop)
  (refresh-all :after 'user/go))

(defn run-fake-services [] ; useful if we want to do a `leun run` or run an uberjar locally
  (when-not fake-services
   (init-fake-services))
  (start-fake-services))

;;;; ___________________________________________________________________________
;;;; App-specific additional utilities for the REPL

(defn get-movies-via-http []
  (http-client/get (str "http://localhost:"
                        (:port @#'main/config)
                        "/api/movies")
                   {:as :json}))

(defn get-movies-via-domain []
  (movies/movies @#'main/config))
