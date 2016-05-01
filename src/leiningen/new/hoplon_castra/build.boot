(set-env!
  :dependencies '[[adzerk/boot-cljs          "{{boot-cljs-v}}"]
                  [adzerk/boot-reload        "{{boot-reload-v}}"]
                  [compojure                 "{{compojure-v}}"]
                  [hoplon/boot-hoplon        "{{boot-hoplon-v}}"]
                  [hoplon/castra             "{{castra-v}}"]
                  [hoplon/hoplon             "{{hoplon-v}}"]
                  [org.clojure/clojure       "{{clojure-v}}"]
                  [org.clojure/clojurescript "{{clojurescript-v}}"]
                  [pandeiro/boot-http        "{{boot-http-v}}"]
                  [ring                      "{{ring-v}}"]
                  [ring/ring-defaults        "{{ring-defaults-v}}"]]
  :resource-paths #{"assets" "src/clj"}
  :source-paths   #{"src/cljs" "src/hl"})

(require
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-reload    :refer [reload]]
  '[hoplon.boot-hoplon    :refer [hoplon prerender]]
  '[pandeiro.boot-http    :refer [serve]])

(deftask dev
  "Build {{raw-name}} for local development."
  []
  (comp
    (serve
      :port    8000
      :handler '{{namespace}}.handler/app
      :reload  true)
    (watch)
    (speak)
    (hoplon)
    (reload)
    (cljs)))

(deftask prod
  "Build {{raw-name}} for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (prerender)))

(deftask make-war
  "Build a war for deployment"
  []
  (comp (hoplon)
        (cljs :optimizations :advanced)
        (uber :as-jars true)
        (web :serve 'my-app.handler/app)
        (war)
        (target :dir #{"target"})))
