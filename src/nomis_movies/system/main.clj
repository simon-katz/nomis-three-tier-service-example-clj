(ns nomis-movies.system.main
  (:require [nomis-movies.system.system :as system]
            [taoensso.timbre :as timbre])
  (:gen-class))

(def ^:private config
  {:this-would-be "stuff grabbed from config files and environment"
   :port 7620
   :fresh-potatoes-service {:port 7621}
   :my-mdb-service         {:port 7622}})

(defn -main
  [& args]
  (assert (= (count args)
             0)
          (format "Expected no args but got these: %s" args))
  (-> (system/make-system config)
      system/start)
  ;; Don't exit:
  @(promise))
