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
         '[tailrecursion.castra.task :as c])

(deftask development
  "Build {{raw-name}} for development."
  []
  (comp (watch) (hoplon {:prerender false}) (c/castra-dev-server '{{namespace}}.api)))

(deftask production
  "Build {{raw-name}} for production."
  []
  (hoplon {:optimizations :advanced}))
