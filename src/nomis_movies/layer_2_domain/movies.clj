(ns nomis-movies.layer-2-domain.movies
  (:require [nomis-movies.layer-2-domain.facades.fresh-potatoes-facade
             :as fresh-potatoes-facade]
            [nomis-movies.layer-2-domain.facades.my-mdb-facade
             :as my-mdb-facade]))

(defn get-movies [config]
  (concat (fresh-potatoes-facade/get-movies config)
          (my-mdb-facade/get-movies config)))

(defn get-movies-in-alphabetical-order [config]
  (sort-by :title
           (get-movies config)))
