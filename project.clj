(defproject nomis-three-tier-service-example-clj "0.1.0-SNAPSHOT"
  :description "An example Clojure service with separate layers for HTTP front end, domain logic and used services"
  :url "https://github.com/simon-katz/nomis-three-tier-service-example-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [bidi "2.1.3" :exclusions [ring/ring-core]]
                 [yada "1.2.11"]]
  :main ^:skip-aot nomis-three-tier-service-example-clj.system.main
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [midje "1.9.1" :exclusions [riddley]]]
                   :plugins [[lein-midje "3.2.1"]]}
             :uberjar {:aot :all}})
