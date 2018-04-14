(ns nomis-three-tier-service-example-clj.layer-3-services.my-mdb
  (:require
   [clj-http.client :as http-client]))

(defn get-movies [config]
  (let [rsp (http-client/get (str "http://localhost:"
                                  (-> config
                                      :my-mdb-service
                                      :port)
                                  "/api/my-mdb-movies")
                             {:as :json})]
    (:body rsp)))
