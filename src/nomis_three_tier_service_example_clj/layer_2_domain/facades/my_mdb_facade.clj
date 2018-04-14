(ns nomis-three-tier-service-example-clj.layer-2-domain.facades.my-mdb-facade
  (:require [nomis-three-tier-service-example-clj.layer-3-services.my-mdb
             :as my-mdb-service]))

(defn get-movies [config]
  ;; TODO Do a transformation here, and use core.spec.
  (my-mdb-service/get-movies config))
