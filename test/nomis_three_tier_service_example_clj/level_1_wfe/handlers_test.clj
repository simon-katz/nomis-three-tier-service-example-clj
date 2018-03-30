(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers-test
  (:require [midje.sweet :refer :all]
            [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as sut]))

(fact sut/x => 42)
