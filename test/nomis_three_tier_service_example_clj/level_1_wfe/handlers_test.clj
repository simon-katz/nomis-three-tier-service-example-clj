(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers-test
  (:require [cheshire.core :as cheshire]
            [midje.sweet :refer :all]
            [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as SUT]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(fact "Test GET request to /movies returns expected response"
  (let [handler  (SUT/make-handler {})
        response (handler (mock/request :get "/api/movies"))
        body     (parse-body (:body response))]
    (:status response) => 200
    body => [{:name "ET"}
             {:name "Citizen Kane"}]))
