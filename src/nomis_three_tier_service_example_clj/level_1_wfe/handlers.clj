(ns nomis-three-tier-service-example-clj.level-1-wfe.handlers
  (:require [yada.yada :as yada]))

;;;; TODO Set up tests as in com.nomistech.clojure-the-language.
;;;;      - But maybe tidy that first.
;;;; TODO Look at Swagger generation.

(def my-resource
  (yada/resource
   {:methods
    {:get
     {:parameters {:query {:name String}
                   :path {:id String}}
      :produces "text/plain"
      :response (fn [ctx]
                  (str "Hello "
                       (get-in ctx [:parameters :query :name])
                       ". The id is "
                       (get-in ctx [:parameters :path :id])
                       ".\n"))}}}))

(defn make-routes [config]

  ["/"

   [[["hello-as-resource" "/" :id] (yada/handler my-resource)]

    [true (yada/as-resource nil)]]])

;;;; TODO Set up some real handler tests.

(def x 42)
