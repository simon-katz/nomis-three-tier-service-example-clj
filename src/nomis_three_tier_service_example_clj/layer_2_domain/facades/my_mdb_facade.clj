(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.my-mdb-facade
  (:require
   [clojure.set :as set]
   [nomis-three-tier-service-example-clj.layer-2-domain.schemas :as schemas]
   [nomis-three-tier-service-example-clj.layer-3-services.my-mdb
    :as my-mdb-service]
   [schema.core :as s]))

(defn get-movies [config]
  (->> (my-mdb-service/get-movies config)
       (map #(set/rename-keys % {:moniker :title}))
       (s/validate [schemas/Movie])))
