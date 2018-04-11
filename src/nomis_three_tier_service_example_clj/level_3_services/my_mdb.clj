(ns nomis-three-tier-service-example-clj.level-3-services.my-mdb
  (:require [clj-http.client :as http-client]))

(defn movies-1 [config]
  (let [rsp (http-client/get (str "http://localhost:"
                                  (-> config
                                      :movie-service-1
                                      :port)
                                  "/api/movies-1")
                             {:as :json})]
    (:body rsp)))
