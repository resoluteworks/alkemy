#!/usr/bin/env bash

set -e

git tag -l | xargs git tag -d
git fetch --tags

VERSION=`cat version.properties | grep "version" | awk -F' *= *' '{print $2}'`
echo "Version is $VERSION"

rm -rf docs/dokka
export COVERALLS_REPO_TOKEN="${COVERALLS_REPO_TOKEN_ALKEMY}"
./gradlew clean dokkaHtml
./gradlew fullBuild publish

git add --all
git commit -am "Release $VERSION"
git push
git tag "${VERSION}"
git push --tags --force

gh release create "${VERSION}" --verify-tag --title "Alkemy ${VERSION}" --notes "Version ${VERSION}"

echo "Finished building version $VERSION"
