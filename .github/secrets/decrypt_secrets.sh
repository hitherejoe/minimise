#!/bin/sh
set -eo pipefail

gpg --quiet --batch --yes --decrypt --passphrase="${{ secrets.IOS_KEYS }}" --output ./.github/secrets/github_actions.mobileprovision.mobileprovision ./.github/secrets/github_actions.mobileprovision.gpg
gpg --quiet --batch --yes --decrypt --passphrase="${{ secrets.IOS_KEYS }}" --output ./.github/secrets/Certificates.p12 ./.github/secrets/Certificates.p12.gpg

mkdir -p ~/Library/MobileDevice/Provisioning\ Profiles

cp ./.github/secrets/github_actions.mobileprovision.mobileprovision ~/Library/MobileDevice/Provisioning\ Profiles/github_actions.mobileprovision.mobileprovision


security create-keychain -p "" build.keychain
security import ./.github/secrets/Certificates.p12 -t agg -k ~/Library/Keychains/build.keychain -P "" -A

security list-keychains -s ~/Library/Keychains/build.keychain
security default-keychain -s ~/Library/Keychains/build.keychain
security unlock-keychain -p "" ~/Library/Keychains/build.keychain

security set-key-partition-list -S apple-tool:,apple: -s -k "" ~/Library/Keychains/build.keychain
