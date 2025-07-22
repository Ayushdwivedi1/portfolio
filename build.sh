#!/bin/bash
set -e

echo "Navigating to Angular project directory..."
cd Ui/portfolio

echo "Installing dependencies..."
npm install

echo "Building Angular application..."
npm run build:prod

echo "Build completed successfully!" 