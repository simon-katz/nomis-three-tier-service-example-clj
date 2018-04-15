(ns nomis-movies.layer-2-domain.facades.my-mdb-facade
  (:require [clojure.set :as set]
            [nomis-movies.domain-schemas.schemas :as schemas]
            [nomis-movies.layer-3-services.my-mdb :as my-mdb-service]
            [schema.core :as s]))

(defn my-mdb-movie->movie [x]
  (set/rename-keys x {:moniker :title}))

(defn get-movies [config]
  (->> (my-mdb-service/get-movies config)
       (map my-mdb-movie->movie)
       (s/validate [schemas/Movie])))
