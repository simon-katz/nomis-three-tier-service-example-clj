(ns nomis-three-tier-service-example-clj.layer-2-domain.movies-test
  (:require
   [midje.sweet :refer :all]
   [nomis-three-tier-service-example-clj.layer-2-domain.movies :as sut]
   [nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
    :as fresh-potatoes-service]
   [nomis-three-tier-service-example-clj.layer-3-services.my-mdb
    :as my-mdb-service]))

(fact "`get-movies` combines movies from fresh-potatoes and my-mdb"
  (sut/get-movies ..config..)
  => [{:name "C"}
      {:name "B"}
      {:name "D"}
      {:name "A"}]
  (provided (fresh-potatoes-service/get-movies ..config..)
            => [{:name "C"}
                {:name "B"}]
            (my-mdb-service/get-movies ..config..)
            => [{:name "D"}
                {:name "A"}]))

(fact "`get-movies-in-alphabetical-order` combines and sorts movies from fresh-potatoes and my-mdb"
  (sut/get-movies-in-alphabetical-order ..config..)
  => [{:name "A"}
      {:name "B"}
      {:name "C"}
      {:name "D"}]
  (provided (fresh-potatoes-service/get-movies ..config..)
            => [{:name "C"}
                {:name "B"}]
            (my-mdb-service/get-movies ..config..)
            => [{:name "D"}
                {:name "A"}]))
