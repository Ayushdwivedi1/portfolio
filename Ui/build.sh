#!/bin/bash
set -e

echo "Installing dependencies..."
npm install

echo "Building Angular application..."
npm run build

echo "Build completed successfully!" 