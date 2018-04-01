(ns nomis-three-tier-service-example-clj.system.main
  (:require [nomis-three-tier-service-example-clj.system.system :as system]
            [taoensso.timbre :as timbre])
  (:gen-class))

;;;; TODO Setup config.

;;;; TODO Two things called config.
;;;;      - Call the other `ctx`.

(def ^:private config
  {:this-would-be "stuff grabbed from config files and environment"})

(defn -main
  [& args]
  (assert (= (count args)
             0)
          (format "Expected no args but got these: %s" args))
  (-> (system/make-system config)
      system/start)
  ;; Don't exit:
  @(promise))
