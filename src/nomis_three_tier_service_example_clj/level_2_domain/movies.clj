(ns nomis-three-tier-service-example-clj.level-2-domain.movies
  (:require [nomis-three-tier-service-example-clj.level-3-services.fresh-potatoes
             :as fresh-potatoes-service]
            [nomis-three-tier-service-example-clj.level-3-services.my-mdb
             :as my-mdb-service]))

(defn movies [config]
  (concat (fresh-potatoes-service/movies-2 config)
          (my-mdb-service/movies-1 config)))
