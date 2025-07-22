#!/bin/bash
set -e

echo "Navigating to Angular project directory..."
cd Ui

echo "Installing dependencies..."
npm install

echo "Building Angular application..."
npm run build

echo "Build completed successfully!" 