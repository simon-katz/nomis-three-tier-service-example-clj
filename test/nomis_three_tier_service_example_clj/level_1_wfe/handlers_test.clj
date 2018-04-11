(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers-test
  (:require [cheshire.core :as cheshire]
            [clj-http.client :as http-client]
            [midje.sweet :refer :all]
            [nomis-three-tier-service-example-clj.level-1-wfe.handlers :as SUT]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(fact "Test GET request to /api/movies returns expected response"
  (let [config   {:fresh-potatoes-service {:port 1001}
                  :my-mdb-service {:port 1002}}
        handler  (SUT/make-handler config)
        response (handler (mock/request :get "/api/movies"))
        body     (parse-body (:body response))]
    [(:status response)
     body])
  =>
  [200
   [{:name "Movie A"}
    {:name "Movie B"}
    {:name "Movie C"}
    {:name "Movie D"}]]
  (provided (http-client/get "http://localhost:1001/api/movies-2"
                             {:as :json})
            => {:body [{:name "Movie A"}
                       {:name "Movie B"}]}
            (http-client/get "http://localhost:1002/api/movies-1"
                             {:as :json})
            => {:body [{:name "Movie C"}
                       {:name "Movie D"}]}))
