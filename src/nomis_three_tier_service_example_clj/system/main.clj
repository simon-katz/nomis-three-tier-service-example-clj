(ns nomis-three-tier-service-example-clj.system.main
  (:gen-class))

;;;; TODO Setup config.

;;;; TODO Two things called config; need better naming:
;;;;      - Conventional config
;;;;      - The "global" state that you pass around everywhere.

(def ^:private the-conventional-config {})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
