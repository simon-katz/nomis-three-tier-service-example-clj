(ns nomis-movies.layer-2-domain.movies
  (:require [clojure.set :as set]
            [nomis-movies.layer-2-domain.schemas :as schemas]
            [schema.core :as s]))

(defn ^:private canonicalize-fresh-potatoes-movie [x]
  (->> (set/rename-keys x {:name :title})
       (s/validate schemas/Movie)))

(defn ^:private canonicalize-my-mdb-movie [x]
  (->> (set/rename-keys x {:moniker :title})
       (s/validate schemas/Movie)))

(defn combine-raw-movie-seqs [{:keys [sort?] :as opts}
                                        raw-fresh-potatoes-movies
                                        raw-my-mdb-movies]
  (let [movies (concat (map canonicalize-fresh-potatoes-movie
                            raw-fresh-potatoes-movies)
                       (map canonicalize-my-mdb-movie
                            raw-my-mdb-movies))]
    (if sort?
      (sort-by :title movies)
      movies)))
