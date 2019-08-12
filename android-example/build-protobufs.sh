#!/bin/bash -ex

if [ -z `which realpath` ]; then
    echo "installing realpath"
    brew install coreutils
fi

PROJECT_ROOT=`realpath $(dirname "../")`
SCRIPT_DIR=`realpath $(dirname "${0}")`

PROTOBUF_VERSION="3.6.0"  # Must match build.gradle

rm -rf "build"
mkdir "build"

# Download protobuf compiler
pushd $PROJECT_ROOT/build
curl -f -L -O "https://github.com/google/protobuf/releases/download/v${PROTOBUF_VERSION}/protoc-${PROTOBUF_VERSION}-osx-x86_64.zip"
unzip -o "protoc-${PROTOBUF_VERSION}-osx-x86_64.zip"
popd

# Generate Java files
rm -rf $PROJECT_ROOT/protobufs/src/main/java/*
$PROJECT_ROOT/build/bin/protoc "--java_out=$PROJECT_ROOT/protobufs/src/main/java/" -I "$PROJECT_ROOT/proto" "$PROJECT_ROOT/proto/example.proto"
