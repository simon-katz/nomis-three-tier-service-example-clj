(ns nomis-movies.layer-2-domain.movies
  (:require [clojure.set :as set]
            [nomis-movies.domain-schemas.schemas :as schemas]
            [nomis-movies.layer-3-services.fresh-potatoes
             :as
             fresh-potatoes-service]
            [nomis-movies.layer-3-services.my-mdb :as my-mdb-service]
            [schema.core :as s]))

(defn ^:private canonicalize-fresh-potatoes-movie [x]
  (->> (set/rename-keys x {:name :title})
       (s/validate schemas/Movie)))

(defn ^:private canonicalize-my-mdb-movie [x]
  (->> (set/rename-keys x {:moniker :title})
       (s/validate schemas/Movie)))

(defn ^:private combine-raw-movie-seqs [{:keys [sort?] :as opts}
                                        raw-fresh-potatoes-movies
                                        raw-my-mdb-movies]
  (let [movies (concat (map canonicalize-fresh-potatoes-movie
                            raw-fresh-potatoes-movies)
                       (map canonicalize-my-mdb-movie
                            raw-my-mdb-movies))]
    (if sort?
      (sort-by :title movies)
      movies)))

(defn get-movies [config opts]
  (combine-raw-movie-seqs opts
                          (fresh-potatoes-service/get-movies config)
                          (my-mdb-service/get-movies config)))
