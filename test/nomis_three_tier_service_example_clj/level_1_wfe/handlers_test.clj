(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers-test
  (:require [cheshire.core :as cheshire]
            [midje.sweet :refer :all]
            [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as SUT]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(facts "Compojure api tests"

  (fact "Test GET request to /hello?name={a-name} returns expected response"
    (let [app      (SUT/make-routes {})
          response (app (-> (mock/request :get  "/api/plus?x=1&y=2")))
          body     (parse-body (:body response))]
      (:status response) => 200
      (:result body)    => 3)))
