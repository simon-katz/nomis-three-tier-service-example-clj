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
  => [{:title "C"}
      {:title "B"}
      {:title "D"}
      {:title "A"}]
  (provided (fresh-potatoes-service/get-movies ..config..)
            => [{:name "C"}
                {:name "B"}]
            (my-mdb-service/get-movies ..config..)
            => [{:moniker "D"}
                {:moniker "A"}]))

(fact "`get-movies-in-alphabetical-order` combines and sorts movies from fresh-potatoes and my-mdb"
  (sut/get-movies-in-alphabetical-order ..config..)
  => [{:title "A"}
      {:title "B"}
      {:title "C"}
      {:title "D"}]
  (provided (fresh-potatoes-service/get-movies ..config..)
            => [{:name "C"}
                {:name "B"}]
            (my-mdb-service/get-movies ..config..)
            => [{:moniker "D"}
                {:moniker "A"}]))
