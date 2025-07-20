#!/bin/bash

# Build script for manual Netlify deployment
echo "🚀 Building portfolio for Netlify deployment..."

# Check Node.js version
echo "📋 Checking Node.js version..."
NODE_VERSION=$(node --version)
echo "Current Node.js version: $NODE_VERSION"

# Check if Node.js version is compatible
if [[ "$NODE_VERSION" == v20* ]] || [[ "$NODE_VERSION" == v22* ]] || [[ "$NODE_VERSION" == v24* ]]; then
    echo "✅ Node.js version is compatible"
else
    echo "❌ Node.js version $NODE_VERSION is not compatible with Angular 20"
    echo "Please upgrade to Node.js 20+ and try again"
    exit 1
fi

# Clean previous build
echo "🧹 Cleaning previous build..."
rm -rf portfolio/dist/

# Install dependencies
echo "📦 Installing dependencies..."
cd portfolio && npm install

# Build the application
echo "🔨 Building application..."
npm run build

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "📁 Build output location: portfolio/dist/portfolio/browser/"
    echo ""
    echo "🌐 To deploy to Netlify:"
    echo "1. Go to https://netlify.com"
    echo "2. Click 'Add new site' > 'Deploy manually'"
    echo "3. Drag and drop the 'portfolio/dist/portfolio/browser' folder"
    echo "4. Your site will be deployed!"
    echo ""
    echo "📋 Files ready for deployment:"
    ls -la portfolio/dist/portfolio/browser/
    echo ""
    echo "🎉 Ready for deployment!"
else
    echo "❌ Build failed!"
    exit 1
fi 