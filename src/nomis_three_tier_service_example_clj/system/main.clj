(ns nomis-three-tier-service-example-clj.system.main
  (:require [nomis-three-tier-service-example-clj.system.system :as system]
            [taoensso.timbre :as timbre])
  (:gen-class))

(def ^:private config
  {:this-would-be "stuff grabbed from config files and environment"
   :port 3000})

(defn -main
  [& args]
  (assert (= (count args)
             0)
          (format "Expected no args but got these: %s" args))
  (-> (system/make-system config)
      system/start)
  ;; Don't exit:
  @(promise))
