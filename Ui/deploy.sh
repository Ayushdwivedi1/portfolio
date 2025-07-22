#!/bin/bash

# Portfolio Deployment Script for Render
# This script helps prepare your application for deployment

echo "🚀 Portfolio Deployment Script"
echo "================================"

# Check if we're in the right directory
if [ ! -f "package.json" ]; then
    echo "❌ Error: package.json not found. Please run this script from the Ui directory."
    exit 1
fi

echo "📦 Building Frontend (Angular)..."
npm install
npm run build:prod

if [ $? -eq 0 ]; then
    echo "✅ Frontend build completed successfully!"
else
    echo "❌ Frontend build failed!"
    exit 1
fi

echo ""
echo "🔧 Building Backend (Spring Boot)..."
cd Backend/portfolio-microservice

# Check if Maven wrapper exists
if [ ! -f "mvnw" ]; then
    echo "❌ Error: Maven wrapper not found. Please ensure you're in the correct directory."
    exit 1
fi

# Make mvnw executable
chmod +x mvnw

# Build the backend
./mvnw clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Backend build completed successfully!"
    echo "📁 JAR file created at: target/portfolio-0.0.1-SNAPSHOT.jar"
else
    echo "❌ Backend build failed!"
    exit 1
fi

cd ../..

echo ""
echo "🎉 Build process completed successfully!"
echo ""
echo "📋 Next Steps:"
echo "1. Push your code to GitHub"
echo "2. Go to Render dashboard"
echo "3. Create a new Blueprint deployment"
echo "4. Connect your GitHub repository"
echo "5. Render will automatically detect and use render.yaml"
echo ""
echo "📚 For detailed instructions, see: RENDER_DEPLOYMENT_GUIDE.md"
echo ""
echo "⚠️  Remember to set up your environment variables in Render:"
echo "   - DATABASE_URL"
echo "   - DATABASE_USERNAME" 
echo "   - DATABASE_PASSWORD"
echo "   - GMAIL_USERNAME"
echo "   - GMAIL_APP_PASSWORD" 