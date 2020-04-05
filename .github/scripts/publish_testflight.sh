#!/bin/bash

set -eo pipefail

xcrun altool --upload-app -t ios -f native/KotlinIOS/DerivedData/Minimise.ipa -u "$APPLEID_USERNAME" -p "$APPLEID_PASSWORD" --verbose