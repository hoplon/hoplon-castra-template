(ns {{namespace}}.handler
  (:require
   [castra.middleware              :refer [wrap-castra]]
   [clojure.java.io                :as    io]
   [compojure.core                 :refer [defroutes GET]]
   [compojure.route                :refer [resources not-found]]
   [ring.middleware.defaults       :refer [wrap-defaults api-defaults]]
   [ring.middleware.resource       :refer [wrap-resource]]
   [ring.middleware.session        :refer [wrap-session]]
   [ring.middleware.session.cookie :refer [cookie-store]]
   [ring.util.response             :refer [content-type resource-response]]))

(defroutes app-routes
  (GET "/" req
    (-> "index.html"
        (resource-response)
        (content-type "text/html")))
  (resources "/" {:root ""})
  (not-found (or (io/resource "public/404.html")
                 "Oups! This page doesn't exist! (404 error)")))

(def app
  (-> app-routes
      (wrap-castra '{{namespace}}.api)
      (wrap-session {:store (cookie-store "a 16-byte secret")})
      (wrap-defaults api-defaults)
      (wrap-resource "public")))
