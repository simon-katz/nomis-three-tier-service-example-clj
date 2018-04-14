(ns fake-external-services.layer-1-wfe.fake-my-mdb-handler
  (:require
   [compojure.api.sweet :as c]
   [ring.util.http-response :as rur]))

(defn make-handler [config]
  (c/api
   (c/context "/api" []
     (c/GET "/my-mdb-movies" []
       :summary "Provide list of all movies"
       (rur/ok [{:moniker "ET"}
                {:moniker "Citizen Kane"}])))))
