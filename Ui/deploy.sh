#!/bin/bash

# Deployment script for Netlify
echo "🚀 Starting deployment process..."

# Clean previous build
echo "🧹 Cleaning previous build..."
rm -rf dist/

# Install dependencies
echo "📦 Installing dependencies..."
npm install

# Build the application
echo "🔨 Building application..."
npm run build

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📁 Build output location: dist/portfolio/browser/"
    echo ""
    echo "🌐 To deploy to Netlify:"
    echo "1. Go to https://netlify.com"
    echo "2. Drag and drop the 'dist/portfolio/browser' folder"
    echo "3. Or use Netlify CLI: netlify deploy --prod --dir=dist/portfolio/browser"
    echo ""
    echo "📋 Files ready for deployment:"
    ls -la dist/portfolio/browser/
else
    echo "❌ Build failed!"
    exit 1
fi 