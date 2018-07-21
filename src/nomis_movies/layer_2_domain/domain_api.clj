(ns nomis-movies.layer-2-domain.domain-api
  (:require [nomis-movies.layer-2-domain.movies :as movies]
            [nomis-movies.layer-3-services.fresh-potatoes
             :as
             fresh-potatoes-service]
            [nomis-movies.layer-3-services.my-mdb :as my-mdb-service]))

;;;; TODO This is simplistic.
;;;;      In general, the services you call and how you call them will depend
;;;;      the request. You will want to do domain logic to work out what calls
;;;;      you want to make.
;;;;      So was this pure-functional-ising a waste of time?

(defn get-movies [config opts]
  (movies/combine-raw-movie-seqs opts
                                 (fresh-potatoes-service/get-movies config)
                                 (my-mdb-service/get-movies config)))
