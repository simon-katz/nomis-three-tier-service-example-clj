(ns fake-external-services.layer-1-wfe.fake-fresh-potatoes-handler
  (:require
   [compojure.api.sweet :as c]
   [ring.util.http-response :as rur]))

(defn make-handler [config]
  (c/api
   (c/context "/api" []
     (c/GET "/fresh-potatoes-movies" []
       :summary "Provide list of all movies"
       (rur/ok [{:name "Groundhog Day"}
                {:name "Roman Holiday"}])))))
