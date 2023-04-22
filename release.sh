#!/usr/bin/env bash

set -e

./gradlew incrementSemanticVersion --patch
VERSION=`cat version.properties | grep "version" | awk -F' *= *' '{print $2}'`
echo "Version is $VERSION"

sed -i '' "s/alkemyVersion.*/alkemyVersion = $VERSION/g" sample-project/gradle.properties
./gradlew clean build publish
cd sample-project
./gradlew clean test
