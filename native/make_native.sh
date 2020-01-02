#!/usr/bin/env bash
set -x
if [[ $HASHEMI_BUILD_NATIVE == "false" ]]; then
    echo "Skipping the native image build because HASHEMI_BUILD_NATIVE is set to false."
    exit 0
fi


OPTS=""
if [[ $(uname) != "Darwin" ]]; then
  OPTS="--static"
fi


"$JAVA_HOME"/bin/native-image \
    -H:ReflectionConfigurationFiles=reflection.json \
    --macro:truffle --no-fallback --initialize-at-build-time \
    -cp ../language/target/hashem.jar:../launcher/target/hashem-launcher.jar \
    ninja.soroosh.hashem.lang.launcher.HashemiMain \
    $OPTS hashem

