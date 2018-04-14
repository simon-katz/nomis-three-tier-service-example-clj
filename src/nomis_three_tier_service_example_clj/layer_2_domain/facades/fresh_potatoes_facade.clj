(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.fresh-potatoes-facade
  (:require
   [clojure.set :as set]
   [nomis-three-tier-service-example-clj.layer-2-domain.schemas :as schemas]
   [nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
    :as fresh-potatoes-service]
   [schema.core :as s]))

(defn get-movies [config]
  (->> (fresh-potatoes-service/get-movies config)
       (map #(set/rename-keys % {:name :title}))
       (s/validate [schemas/Movie])))

