(ns nomis-three-tier-service-example-clj.layer-2-domain.schemas
  (:require
   [schema.core :as s]))

(s/defschema Movie
  {:title                         s/Str
   (s/optional-key :description)  s/Str
   (s/optional-key :director)     s/Str
   (s/optional-key :release-date) s/Str ; TODO Maybe make this a date
   (s/optional-key :genre)        (s/enum :art-house
                                          :chick-flick
                                          :comedy
                                          :documentary
                                          :fantasy
                                          :horror
                                          :musical
                                          :other
                                          :science-fiction)
   (s/optional-key :country)      s/Str})
