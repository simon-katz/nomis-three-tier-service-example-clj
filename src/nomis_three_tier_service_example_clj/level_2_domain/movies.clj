(ns nomis-three-tier-service-example-clj.level-2-domain.movies
  (:require [nomis-three-tier-service-example-clj.level-3-services.movie-service-1 :as movie-service-1]
            [nomis-three-tier-service-example-clj.level-3-services.movie-service-2 :as movie-service-2]))

(defn movies [config]
  (concat (movie-service-1/movies-1 config)
          (movie-service-2/movies-2 config)))
