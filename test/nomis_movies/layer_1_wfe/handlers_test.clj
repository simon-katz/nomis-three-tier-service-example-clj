(ns nomis-movies.layer-1-wfe.handlers-test
  (:require [cheshire.core :as cheshire]
            [clj-http.client :as http-client]
            [midje.sweet :refer :all]
            [nomis-movies.layer-1-wfe.handlers :as sut]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(fact "GET request to /favicon.ico returns expected response"
  (let [config   {}
        handler  (sut/make-handler config)
        response (handler (mock/request :get "/favicon.ico"))]
    response)
  =>
  {:status 200
   :headers {}
   :body nil})

(fact "GET request to /api/movies returns expected response"
  (let [config   {:fresh-potatoes-service {:port 1001}
                  :my-mdb-service {:port 1002}}
        handler  (sut/make-handler config)
        response (handler (mock/request :get "/api/movies"))
        body     (parse-body (:body response))]
    [(:status response)
     body])
  =>
  [200
   [{:title "Movie C"}
    {:title "Movie B"}
    {:title "Movie D"}
    {:title "Movie A"}]]
  (provided (http-client/get "http://localhost:1001/api/fresh-potatoes-movies"
                             {:as :json})
            => {:body [{:name "Movie C"}
                       {:name "Movie B"}]}
            (http-client/get "http://localhost:1002/api/my-mdb-movies"
                             {:as :json})
            => {:body [{:moniker "Movie D"}
                       {:moniker "Movie A"}]}))

(fact "GET request to /api/movies-in-alphabetical-order returns expected response"
  (let [config   {:fresh-potatoes-service {:port 1001}
                  :my-mdb-service {:port 1002}}
        handler  (sut/make-handler config)
        response (handler (mock/request :get "/api/movies-in-alphabetical-order"))
        body     (parse-body (:body response))]
    [(:status response)
     body])
  =>
  [200
   [{:title "Movie A"}
    {:title "Movie B"}
    {:title "Movie C"}
    {:title "Movie D"}]]
  (provided (http-client/get "http://localhost:1001/api/fresh-potatoes-movies"
                             {:as :json})
            => {:body [{:name "Movie C"}
                       {:name "Movie B"}]}
            (http-client/get "http://localhost:1002/api/my-mdb-movies"
                             {:as :json})
            => {:body [{:moniker "Movie D"}
                       {:moniker "Movie A"}]}))
