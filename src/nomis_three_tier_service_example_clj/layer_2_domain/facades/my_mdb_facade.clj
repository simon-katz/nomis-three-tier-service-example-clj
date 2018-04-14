(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.my-mdb-facade
  (:require
   [clojure.set :as set]
   [nomis-three-tier-service-example-clj.layer-3-services.my-mdb
    :as my-mdb-service]))

(defn get-movies [config]
  ;; TODO  Use core.spec on the result.
  (->> (my-mdb-service/get-movies config)
       (map #(set/rename-keys % {:moniker :title}))))
