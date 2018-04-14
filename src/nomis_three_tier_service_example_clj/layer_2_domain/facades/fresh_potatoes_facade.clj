(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.fresh-potatoes-facade
  (:require
   [nomis-three-tier-service-example-clj.layer-3-services.fresh-potatoes
    :as fresh-potatoes-service]))

(defn get-movies [config]
  ;; TODO Do a transformation here, and use core.spec.
  (fresh-potatoes-service/get-movies config))
