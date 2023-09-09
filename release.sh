#!/usr/bin/env bash

set -e

VERSION=`cat version.properties | grep "version" | awk -F' *= *' '{print $2}'`
echo "Version is $VERSION"

rm -rf docs/dokka
export COVERALLS_REPO_TOKEN="${COVERALLS_REPO_TOKEN_ALKEMY}"
./gradlew clean fullBuild dokkaHtml publish

git tag "v${VERSION}" -m "Release v${VERSION}"
git push --tags --force

echo "Finished building version $VERSION"
