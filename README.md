# hoplon-castra

A Leiningen template for creating new Hoplon projects with Castra and deploying them to Heroku.

## Usage

```bash
$ lein new hoplon-castra my-project-name
```

## Deploying (for maintainers)

> Note: to deploy to Clojars you must be a member of the `hoplon-castra` group on Clojars.  Ask @alandipert for access if you need it.

1. Update `+version+` in `build.boot`.  Add the change and push to Github.
1. `boot build-jar push-release` to push the releas to Clojars.  This also creates a tag for the release.
1. `git push --tags` to push the new release tag up to Github.

## License

Copyright © 2013-2016 Micha Niskin, Alan Dipert and Marcelo Nomoto

Distributed under the Eclipse Public License, the same as Clojure.
