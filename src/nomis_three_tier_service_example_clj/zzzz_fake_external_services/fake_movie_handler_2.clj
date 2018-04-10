(ns nomis-three-tier-service-example-clj.zzzz-fake-external-services.fake-movie-handler-2
  (:require [compojure.api.sweet :as c]
            [ring.util.http-response :as rur]))

(defn make-handler [config]
  (c/api
   (c/context "/api" []
     (c/GET "/movies-2" []
       :summary "Provide list of all movies"
       (rur/ok [{:name "Groundhog Day"}
                {:name "Roman Holiday"}])))))
