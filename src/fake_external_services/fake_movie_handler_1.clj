(ns fake-external-services.fake-movie-handler-1
  (:require [compojure.api.sweet :as c]
            [ring.util.http-response :as rur]))

(defn make-handler [config]
  (c/api
   (c/context "/api" []
     (c/GET "/movies-1" []
       :summary "Provide list of all movies"
       (rur/ok [{:name "ET"}
                {:name "Citizen Kane"}])))))
