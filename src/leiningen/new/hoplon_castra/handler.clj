(ns {{namespace}}.handler
  (:require
    [compojure.core                 :refer [defroutes GET]]
    [compojure.route                :refer [resources]]
    [ring.middleware.defaults       :refer [wrap-defaults api-defaults]]
    [ring.util.response             :refer [content-type resource-response]]
    [ring.middleware.session        :refer [wrap-session]]
    [ring.middleware.session.cookie :refer [cookie-store]]
    [castra.middleware              :refer [wrap-castra]]))

(defroutes app-routes
  (GET "/" req
    (-> "index.html"
        (resource-response)
        (content-type "text/html")))
  (resources "/" {:root ""}))

(def app
  (-> app-routes
      (wrap-castra '{{namespace}}.api)
      (wrap-session {:store (cookie-store "a 16-byte secret")})
      (wrap-defaults api-defaults)))
