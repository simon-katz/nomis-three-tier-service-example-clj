(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [compojure.api.sweet :as c]
            [nomis-three-tier-service-example-clj.schemas.schemas :as schemas]
            [ring.util.http-response :as rur]))

;;;; ___________________________________________________________________________


;;;; TODO An example that gets similar info from two sources, and combines.
;;;;      Can then do querying on the combined data.
;;;;      Use fake services for the used services.

(defn make-handler [config]
  (c/api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Nomis Movies"
                   :description "A demo of a three-layer app, about movies"}
            :tags [{:name "TODO What should this be? #1"
                    :description "TODO What should this be? #2"}]}}}

   (c/context "/api" []
     :tags ["api"] ; TODO What's this?

     (c/GET "/movies" []
       :return [schemas/Movie]
       :summary "Provide list of all movies"
       (rur/ok [{:name "ET"}
                {:name "Citizen Kane"}])))))
