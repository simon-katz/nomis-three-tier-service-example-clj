(ns user
  (:require [clj-http.client :as http-client]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pp pprint]]
            [clojure.repl :refer :all ; [apropos dir doc find-doc pst source]
             ]
            [clojure.string :as str]
            [clojure.tools.namespace.move :refer :all]
            [clojure.tools.namespace.repl :refer :all]
            [midje.repl :refer :all]
            [nomis-three-tier-service-example-clj.system.main :as main]
            [nomis-three-tier-service-example-clj.system.system :as system]))

;;;; ___________________________________________________________________________
;;;; Clojure workflow.
;;;; See:
;;;; - http://nomistech.blogspot.co.uk/2013/06/stuart-sierras-clojure-development_18.html
;;;; - http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded
;;;; - https://github.com/stuartsierra/component#reloading

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
  (alter-var-root #'the-system
                  (fn [_]
                    (system/make-system @#'main/the-conventional-config))))

(defn start
  "Starts the current development system."
  []
  (alter-var-root #'the-system system/start))

(defn stop
  "Shuts down and destroys the current development system."
  []
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

;;;; ___________________________________________________________________________
;;;; App-specific additional utilities for the REPL

(defn select-keys-rec [m key-paths]
  (apply merge
         (for [p key-paths]
           (let [n (count p)]
             (case n
               0 (throw (Error. "Empty path in key-paths"))
               1 (select-keys m [(first p)])
               (if-not (contains? m (first p))
                 {}
                 {(first p) (select-keys-rec (get m (first p))
                                             (rest p))}))))))

(defn filter-response [response]
  (select-keys-rec response
                   [[:headers ["Content-Type"]]
                    [:body]])) 


(map (fn [get-endpoint]
       [:get-endpoint      get-endpoint
        :filtered-response (-> (http-client/get get-endpoint)
                               filter-response)])
     ["http://localhost:3000/hello-as-resource" 
      "http://localhost:3000/hello-as-handler" 
      "http://localhost:3000/some-plain-text" 
      "http://localhost:3000/an-edn-map-1" 
      "http://localhost:3000/an-edn-map-2"
      "http://localhost:3000/a-json-map"])
