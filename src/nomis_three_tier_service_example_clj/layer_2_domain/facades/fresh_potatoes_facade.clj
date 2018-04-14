(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.fresh-potatoes-facade
  (:require
   [clojure.set :as set]
   [nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
    :as fresh-potatoes-service]))

(defn get-movies [config]
  ;; TODO  Use core.spec on the result.
  (->> (fresh-potatoes-service/get-movies config)
       (map #(set/rename-keys % {:name :title}))))
