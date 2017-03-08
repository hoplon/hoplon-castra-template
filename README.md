# hoplon-castra

A [Boot](http://boot-clj.com) and Leiningen template for creating new Boot-based
Hoplon projects with Castra and deploying them to Heroku.

## Usage

```bash
$ boot -d boot/new new -t hoplon-castra -n my-project-nake
```

## Deployment
To deploy a war file to Heroku:
```bash
$ boot make-war
(first time only) $ heroku plugins:install https://github.com/heroku/heroku-deploy
$ heroku deploy:war --war target/project.war --app <your-app-name>
```
To deploy a standalone `jar`, add the following to your `build.boot` file:
```clojure
(deftask build-jar
  "Builds a standalone jar."
  []
  (comp (aot :namespace #{'app.main})
        (hoplon)
        (cljs :optimizations :advanced)
        (prerender)
        (uber)
        (jar :main 'app.main)
        (sift :include #{#"\.jar$"})
        (target :dir #{"target"})))
```
then
```bash
boot build-jar
heroku deploy:jar --jar target/project.jar --app <your-app-name>
```

### hoplon-castra template deployment for maintainers

> Note: to deploy to Clojars you must be a member of the `hoplon-castra` group on Clojars.  Ask @alandipert for access if you need it.

1. Update `+version+` in `build.boot`.  Add the change and push to Github.
1. `boot build-jar push-release` to push the releas to Clojars.  This also creates a tag for the release.
1. `git push --tags` to push the new release tag up to Github.

## License

Copyright © 2013-2016 Micha Niskin, Alan Dipert and Marcelo Nomoto

Distributed under the Eclipse Public License, the same as Clojure.
