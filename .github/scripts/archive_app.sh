#!/bin/bash

set -eo pipefail

cd native/KotlinIOS

xcodebuild -workspace KotlinIOS.xcworkspace \
            -scheme KotlinIOS \
            -sdk iphoneos \
            -configuration Release \
            -derivedDataPath DerivedData \
            -archivePath DerivedData/Archive/KotlinIOS \
            clean archive | xcpretty