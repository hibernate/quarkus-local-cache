Guide for maintainers
====

This guide is intended for maintainers of this project,
i.e. anybody with direct push access to the git repository.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).

## Continuous integration

Continuous integration happens on a self-hosted Jenkins instance at https://ci.hibernate.org/job/.
Available CI jobs are:
- [Build and test](https://ci.hibernate.org/job/quarkus-local-cache/): runs the builds for pull requests and branches.
- [Dependency update](https://ci.hibernate.org/job/quarkus-local-cache-dependency-update/): updates the dependencies (e.g. Hibernate ORM)
to the latest snapshot version and builds the project. Tests whether there are any incompatible changes in the upcoming releases (of dependencies).

## Release

* Go to [the release job on CI](https://ci.hibernate.org/job/quarkus-local-cache-release/).
* Click the "play" button (green triangle) to launch a build.
* Input the parameters.
* Click "Build".
* If the release option `RELEASE_PUBLISH_AUTOMATICALLY` was selected as `false`-- release the artifacts on the [Maven Central portal](https://central.sonatype.com/).
  * Log into Maven Central. The credentials can be found on Bitwarden; ask a teammate if you don't have access.
  * Click on the profile circle at the top right and pick "View Deployments".
  * Find your deployment on the left and click "Publish".
* If it doesn't work, [call for help](https://hibernate.org/community/#contribute).
