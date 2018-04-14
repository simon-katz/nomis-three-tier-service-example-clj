(ns nomis-three-tier-service-example-clj.layer-1-wfe.handlers
  (:require [compojure.api.sweet :as c]
            [nomis-three-tier-service-example-clj.layer-2-domain.movies
             :as movies]
            [nomis-three-tier-service-example-clj.layer-2-domain.schemas
             :as schemas]
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

   (c/GET "/favicon.ico" [] ; avoid errors when playing with API in a browser
     (rur/ok nil))

   (c/context "/api" []
     :tags ["api"] ; TODO What's this?

     (c/GET "/movies" []
       :return [schemas/Movie]
       :summary "Provide list of all movies"
       (rur/ok (movies/get-movies config)))

     (c/GET "/movies-in-alphabetical-order" []
       :return [schemas/Movie]
       :summary "Provide list of all movies in alphabetical order"
       (rur/ok (movies/get-movies-in-alphabetical-order config))))))
