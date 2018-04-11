(ns fake-external-services.fake-fresh-potatoes-handler
  (:require [compojure.api.sweet :as c]
            [ring.util.http-response :as rur]))

(defn make-handler [config]
  (c/api
   (c/context "/api" []
     (c/GET "/movies-2" []
       :summary "Provide list of all movies"
       (rur/ok [{:name "Groundhog Day"}
                {:name "Roman Holiday"}])))))
