#!/bin/bash

set -eo pipefail

cd native/KotlinIOS

xcrun altool --upload-app -t ios -f DerivedData/Build/Minimise.ipa -u "$APPLEID_USERNAME" -p "$APPLEID_PASSWORD" --verbose
