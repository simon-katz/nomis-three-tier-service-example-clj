(ns nomis-movies.system.system-test
  (:require
   [clj-http.client :as http-client]
   [nomis-movies.layer-2-domain.movies :as movies]
   [nomis-movies.system.system :as sut]
   [midje.sweet :refer :all]
   [taoensso.timbre :as timbre]))

(defn system->jetty-webserver [system]
  (-> system
      :webserver-info
      :jetty-webserver))

(defn jetty-webserver->port [jetty-webserver]
  (-> jetty-webserver
      .getConnectors
      first
      .getLocalPort))

(defmacro with-no-logging [& body]
  `(timbre/with-level :fatal
     ~@body))

(fact "We can create a system and hit an endpoint"
  (let [test-config {:port 0}
        system      (with-no-logging
                      (-> (sut/make-system test-config)
                          sut/start))
        port        (-> system
                        system->jetty-webserver
                        jetty-webserver->port)]
    (try (http-client/get (str "http://localhost:"
                               port
                               "/api/movies")
                          {:as :json})
         (finally
           (with-no-logging
             (sut/stop system))))
    => (contains
        {:status 200
         :body [{:title "Movie A"}
                {:title "Movie B"}]})
    (provided (movies/get-movies test-config)
              => [{:title "Movie A"}
                  {:title "Movie B"}])))
