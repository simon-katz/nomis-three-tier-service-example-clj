(ns nomis-three-tier-service-example-clj.layer-1-wfe.handlers-test
  (:require
   [cheshire.core :as cheshire]
   [clj-http.client :as http-client]
   [midje.sweet :refer :all]
   [nomis-three-tier-service-example-clj.layer-1-wfe.handlers :as sut]
   [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(fact "Test GET request to /api/movies returns expected response"
  (let [config   {:fresh-potatoes-service {:port 1001}
                  :my-mdb-service {:port 1002}}
        handler  (sut/make-handler config)
        response (handler (mock/request :get "/api/movies"))
        body     (parse-body (:body response))]
    [(:status response)
     body])
  =>
  [200
   [{:name "Movie C"}
    {:name "Movie B"}
    {:name "Movie D"}
    {:name "Movie A"}]]
  (provided (http-client/get "http://localhost:1001/api/fresh-potatoes-movies"
                             {:as :json})
            => {:body [{:name "Movie C"}
                       {:name "Movie B"}]}
            (http-client/get "http://localhost:1002/api/my-mdb-movies"
                             {:as :json})
            => {:body [{:name "Movie D"}
                       {:name "Movie A"}]}))

(fact "Test GET request to /api/movies-in-alphabetical-order returns expected response"
  (let [config   {:fresh-potatoes-service {:port 1001}
                  :my-mdb-service {:port 1002}}
        handler  (sut/make-handler config)
        response (handler (mock/request :get "/api/movies-in-alphabetical-order"))
        body     (parse-body (:body response))]
    [(:status response)
     body])
  =>
  [200
   [{:name "Movie A"}
    {:name "Movie B"}
    {:name "Movie C"}
    {:name "Movie D"}]]
  (provided (http-client/get "http://localhost:1001/api/fresh-potatoes-movies"
                             {:as :json})
            => {:body [{:name "Movie C"}
                       {:name "Movie B"}]}
            (http-client/get "http://localhost:1002/api/my-mdb-movies"
                             {:as :json})
            => {:body [{:name "Movie D"}
                       {:name "Movie A"}]}))
