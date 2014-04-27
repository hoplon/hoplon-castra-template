(ns leiningen.new.hoplon-castra
  (:require [leiningen.new.templates :as t]
            [ancient-clj.core        :refer [latest-version-string!]]))

(def deps
  '[tailrecursion/boot.core
    tailrecursion/boot.task
    tailrecursion/hoplon
    org.clojure/clojurescript
    tailrecursion/boot.ring])

(defn latest-deps-strs [deps]
  (mapv (partial latest-version-string! {:snapshots? false}) deps))

(defn hoplon-castra
  "Create new Hoplon project."
  [name]
  (let [[boot-core-v boot-task-v
         hoplon-v clojurescript-v
         boot-ring-v]              (->> deps
                                        latest-deps-strs)
        render  (t/renderer "hoplon-castra")
        main-ns (t/sanitize-ns name)
        data    {:raw-name    name
                 :boot-core-v boot-core-v
                 :namespace   main-ns
                 :nested-dirs (t/name-to-path main-ns)
                 :boot-task-v boot-task-v
                 :hoplon-v    hoplon-v
                 :clojurescript-v clojurescript-v
                 ; boot.ring only has a SNAPSHOT version
                 ;:boot-ring-v boot-ring-v
                 :boot-ring-v "0.1.0-SNAPSHOT"
                 :dependencies (latest-deps-strs deps)
                 :require-tasks '#{[tailrecursion.boot.task :refer :all]
                                   [tailrecursion.hoplon.boot :refer :all]}
                 :name        (t/project-name name)
                 :year        (t/year)}]
    (t/->files data
               ["README.md"           (render "README.md"      data)]
               ["build.boot"          (render "build.boot"     data)]
               ["assets/css/main.css" (render "main.css"       data)]
               [".gitignore"          (render "gitignore"      data)]
               ["src/hl/{{nested-dirs}}/index.cljs.hl" (render "index.cljs.hl"  data)]
               ["src/clj/{{nested-dirs}}/api.clj"      (render "api.clj"  data)]
               ["src/clj/{{nested-dirs}}/core.clj"     (render "core.clj" data)]
               ["src/cljs/{{nested-dirs}}/rpc.cljs"    (render "rpc.cljs" data)])))
