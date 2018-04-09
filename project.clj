(defproject nomis-three-tier-service-example-clj "0.1.0-SNAPSHOT"
  :description "An example Clojure service with separate layers for HTTP front end, domain logic and used services"
  :url "https://github.com/simon-katz/nomis-three-tier-service-example-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[cheshire "5.8.0"]
                 [clj-http "3.8.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [metosin/compojure-api "1.1.11"]
                 [org.clojure/clojure "1.9.0"]
                 [ring "1.6.3"]]
  :main ^:skip-aot nomis-three-tier-service-example-clj.system.main
  :repl-options {:init-ns user}
  :target-path "target/%s"
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [midje "1.9.1"]
                                  [ring/ring-mock "0.3.2"]]
                   :plugins [[lein-midje "3.2.1"]]}
             :uberjar {:aot :all}})
