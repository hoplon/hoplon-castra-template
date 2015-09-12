(ns {{namespace}}.handler
  (:require
    [compojure.core :as c]
    [compojure.route :as route]
    [ring.middleware.defaults :as d]
    [ring.util.response :as response]
    [castra.middleware :as castra]))

(c/defroutes app-routes
  (c/GET "/" req (response/content-type (response/resource-response "index.html") "text/html"))
  (route/resources "/" {:root ""}))

(def app
  (-> app-routes
      (d/wrap-defaults d/api-defaults)
      (castra/wrap-castra-session "a 16-byte secret")
      (castra/wrap-castra '{{namespace}}.api)))
