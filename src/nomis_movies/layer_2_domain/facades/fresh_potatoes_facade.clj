(ns nomis-movies.layer-2-domain.facades.fresh-potatoes-facade
  (:require
   [clojure.set :as set]
   [nomis-movies.domain-schemas.schemas :as schemas]
   [nomis-movies.layer-3-services.fresh-potatoes
    :as fresh-potatoes-service]
   [schema.core :as s]))

(defn fresh-potatoes-movie->movie [x]
  (set/rename-keys x {:name :title}))

(defn get-movies [config]
  (->> (fresh-potatoes-service/get-movies config)
       (map fresh-potatoes-movie->movie)
       (s/validate [schemas/Movie])))

