(ns nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
  (:require [clj-http.client :as http-client]))

(defn get-movies [config]
  (let [rsp (http-client/get (str "http://localhost:"
                                  (-> config
                                      :fresh-potatoes-service
                                      :port)
                                  "/api/fresh-potatoes-movies")
                             {:as :json})]
    (:body rsp)))
