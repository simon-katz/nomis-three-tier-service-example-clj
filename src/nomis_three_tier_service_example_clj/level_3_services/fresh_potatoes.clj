(ns nomis-three-tier-service-example-clj.level-3-services.fresh-potatoes
  (:require [clj-http.client :as http-client]))

(defn movies-2 [config]
  (let [rsp (http-client/get (str "http://localhost:"
                                  (-> config
                                      :fresh-potatoes-service
                                      :port)
                                  "/api/movies-2")
                             {:as :json})]
    (:body rsp)))
