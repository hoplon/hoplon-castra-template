(ns leiningen.new.hoplon-castra
  (:require [leiningen.new.templates :as t]))

(defn hoplon-castra
  "Create new Hoplon project with Castra."
  [name]
  (let [boot-cljs-v      "1.7.228-2"
        boot-cljs-repl-v "0.3.3"
        boot-core-v      "2.7.1"
        boot-http-v      "0.7.6"
        boot-reload-v    "0.5.1"
        castra-v         "3.0.0-SNAPSHOT"
        clojure-v        "1.8.0"
        clojurescript-v  "1.9.495"
        compojure-v      "1.6.0-beta3"
        hoplon-v         "6.0.0-alpha17"
        piggieback-v     "0.2.1"
        ring-v           "1.5.1"
        ring-defaults-v  "0.2.3"
        tools-nrepl-v    "0.2.12"
        weasel-v         "0.7.0"
        render  (t/renderer "hoplon-castra")
        main-ns (t/sanitize-ns name)
        data    {:raw-name         name
                 :boot-cljs-v      boot-cljs-v
                 :boot-cljs-repl-v boot-cljs-repl-v
                 :boot-core-v      boot-core-v
                 :boot-http-v      boot-http-v
                 :boot-reload-v    boot-reload-v
                 :castra-v         castra-v
                 :clojure-v        clojure-v
                 :clojurescript-v  clojurescript-v
                 :compojure-v      compojure-v
                 :hoplon-v         hoplon-v
                 :piggieback-v     piggieback-v
                 :ring-v           ring-v
                 :ring-defaults-v  ring-defaults-v
                 :tools-nrepl-v    tools-nrepl-v
                 :weasel-v         weasel-v
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
