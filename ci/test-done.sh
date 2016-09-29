#!/usr/bin/env bash

set -e -x

pushd done-source
    ./gradlew clean test
popd
