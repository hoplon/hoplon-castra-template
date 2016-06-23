(ns leiningen.new.hoplon-castra
  (:require [leiningen.new.templates :as t]
            [ancient-clj.core        :refer [latest-version-string!]]))

(def deps
  '[adzerk/boot-cljs
    adzerk/boot-reload
    boot/core
    compojure
    hoplon/boot-hoplon
    hoplon/castra
    hoplon/hoplon
    org.clojure/clojurescript
    pandeiro/boot-http
    ring/ring
    ring/ring-defaults])

(defn latest-deps-strs [deps]
  (mapv #(latest-version-string! % {:snapshots? false}) deps))

(defn hoplon-castra
  "Create new Hoplon project with Castra."
  [name]
  (let [[boot-cljs-v
         boot-reload-v
         boot-core-v
         compojure-v
         boot-hoplon-v
         castra-v
         hoplon-v
         clojurescript-v
         boot-http-v
         ring-v
         ring-defaults-v] (latest-deps-strs deps)
        castra-v "3.0.0-SNAPSHOT"
        clojure-v "1.8.0"
        render  (t/renderer "hoplon-castra")
        main-ns (t/sanitize-ns name)
        data    {:raw-name        name
                 :boot-cljs-v      boot-cljs-v
                 :boot-core-v      boot-core-v
                 :boot-hoplon-v    boot-hoplon-v
                 :boot-http-v      boot-http-v
                 :boot-reload-v    boot-reload-v
                 :castra-v         castra-v
                 :clojure-v        clojure-v
                 :clojurescript-v  clojurescript-v
                 :compojure-v      compojure-v
                 :hoplon-v         hoplon-v
                 :ring-defaults-v  ring-defaults-v
                 :ring-v           ring-v
                 :namespace        main-ns
                 :nested-dirs      (t/name-to-path main-ns)
                 :name             (t/project-name name)
                 :year             (t/year)}]
    (t/->files data
               ["README.md"           (render "README.md"       data)]
               ["build.boot"          (render "build.boot"      data)]
               ["boot.properties"     (render "boot.properties" data)]
               ["resources/public/app.css"      (render "app.css"         data)]
               ["resources/public/404.html"      (render "404.html"         data)]
               [".gitignore"          (render "gitignore"       data)]
               ["src/hl/{{nested-dirs}}/index.cljs.hl" (render "index.cljs.hl"  data)]
               ["src/clj/{{nested-dirs}}/api.clj"      (render "api.clj"  data)]
               ["src/clj/{{nested-dirs}}/core.clj"     (render "core.clj" data)]
               ["src/clj/{{nested-dirs}}/handler.clj"  (render "handler.clj" data)]
               ["src/cljs/{{nested-dirs}}/rpc.cljs"    (render "rpc.cljs" data)])))
