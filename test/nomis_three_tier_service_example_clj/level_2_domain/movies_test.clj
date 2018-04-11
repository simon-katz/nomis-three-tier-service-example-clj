(ns nomis-three-tier-service-example-clj.level-2-domain.movies-test
  (:require [nomis-three-tier-service-example-clj.level-2-domain.movies :as sut]
            [nomis-three-tier-service-example-clj.level-3-services.fresh-potatoes
             :as fresh-potatoes-service]
            [nomis-three-tier-service-example-clj.level-3-services.my-mdb
             :as my-mdb-service][midje.sweet :refer :all]))

(fact "`movies` combines movies from fresh-potatoes and my-mdb"
  (sut/movies ..config..)
  => [..film-1..
      ..film-2..
      ..film-3..
      ..film-4..]
  (provided (fresh-potatoes-service/get-movies ..config..) => [..film-1..
                                                               ..film-2..]
            (my-mdb-service/get-movies ..config..) => [..film-3..
                                                       ..film-4..]))
