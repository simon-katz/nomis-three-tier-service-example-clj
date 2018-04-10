(ns nomis-three-tier-service-example-clj.level-3-services.movie-service-2
  (:require [clj-http.client :as http-client]))

(defn movies-2 [config]
  (let [rsp (http-client/get (str "http://localhost:"
                                  (-> config
                                      :movie-service-2
                                      :port)
                                  "/api/movies-2")
                             {:as :json})]
    (:body rsp)))
