(ns nomis-movies.layer-2-domain.movies-test
  (:require [midje.sweet :refer :all]
            [nomis-movies.layer-2-domain.movies :as sut]
            [nomis-movies.layer-3-services.fresh-potatoes
             :as fresh-potatoes-service]
            [nomis-movies.layer-3-services.my-mdb :as my-mdb-service]))

(fact "`sut/combine-raw-movie-seqs` combines fresh-potatoes and my-mdb movies"
  (#'sut/combine-raw-movie-seqs {}
                                [{:name "C"}
                                 {:name "B"}]
                                [{:moniker "D"}
                                 {:moniker "A"}])
  => [{:title "C"}
      {:title "B"}
      {:title "D"}
      {:title "A"}])

(fact "`sut/combine-raw-movie-seqs` sorts the result when requested"
  (#'sut/combine-raw-movie-seqs {:sort? true}
                                [{:name "C"}
                                 {:name "B"}]
                                [{:moniker "D"}
                                 {:moniker "A"}])
  => [{:title "A"}
      {:title "B"}
      {:title "C"}
      {:title "D"}])
