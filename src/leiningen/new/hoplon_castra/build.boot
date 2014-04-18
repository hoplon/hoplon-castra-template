#!/usr/bin/env boot

#tailrecursion.boot.core/version "{{boot-core-v}}"

(set-env!
  :project      '{{raw-name}}
  :version      "0.1.0-SNAPSHOT"
  :dependencies '[[tailrecursion/boot.task   "{{boot-task-v}}"]
                  [tailrecursion/hoplon      "{{hoplon-v}}"]
                  [org.clojure/clojurescript "{{clojurescript-v}}"]
                  [tailrecursion/boot.ring   "{{boot-ring-v}}"]]
  :out-path     "resources/public"
  :src-paths    #{"src/hl" "src/cljs" "src/clj"})

;; Static resources (css, images, etc.):
(add-sync! (get-env :out-path) #{"assets"})

(require '[tailrecursion.hoplon.boot :refer :all]
         '[tailrecursion.castra.handler   :as c]
         '[tailrecursion.boot.task.ring   :as r])

(deftask castra
  [& specs]
  (r/ring-task (fn [_] (apply c/castra specs))))

(deftask development
  "Build {{raw-name}} for development."
  []
  (comp (watch) (hoplon {:prerender false})
        (r/head) (r/dev-mode) (r/session-cookie) (r/files)
        (castra '{{namespace}}.api) (r/jetty)))

(deftask production
  "Build {{raw-name}} for production."
  []
  (hoplon {:optimizations :advanced}))
