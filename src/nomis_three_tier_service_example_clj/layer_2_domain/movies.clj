(ns nomis-three-tier-service-example-clj.layer-2-domain.movies
  (:require [nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
             :as fresh-potatoes-service]
            [nomis-three-tier-service-example-clj.layer-3-services.my-mdb
             :as my-mdb-service]))

(defn movies [config]
  (concat (fresh-potatoes-service/get-movies config)
          (my-mdb-service/get-movies config)))

(defn movies-in-alphabetical-order [config]
  (sort-by :name
           (movies config)))
