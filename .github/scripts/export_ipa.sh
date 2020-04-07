#!/bin/bash

set -eo pipefail

cd native/KotlinIOS

ls

xcodebuild -archivePath DerivedData/Archive/Minimise.xcarchive \
            -exportOptionsPlist KotlinIOS/exportOptions.plist \
            -exportPath DerivedData/Build \
            -allowProvisioningUpdates \
            -exportArchive | xcpretty


ls DerivedData/Build
