(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [compojure.api.sweet :as c]
            [ring.util.http-response :as rur]
            [schema.core :as s]))

;;;; ___________________________________________________________________________

;;;; Example building on compojure-api template stuff.

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(defn make-handler [config]
  (c/api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Compojure-api-play"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]}}}

   (c/context "/api" []
     :tags ["api"]

     (c/GET "/plus" []
       :return {:result Long}
       :query-params [x :- Long, y :- Long]
       :summary "adds two numbers together"
       (rur/ok {:result (+ x y)}))

     (c/POST "/echo" []
       :return Pizza
       :body [pizza Pizza]
       :summary "echoes a Pizza"
       (rur/ok pizza))

     (c/GET "/hello-as-resource/:id" [id]
       :query-params [name :- String]
       :summary "An endpoint with a parameter in the URL and a query parameter"
       (rur/ok (str "Hello "
                    name
                    ". The id is "
                    id
                    ".\n"))))))
