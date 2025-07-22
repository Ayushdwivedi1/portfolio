#!/bin/bash

# Script to update API URL for production deployment
echo "🔧 Updating API URL for production deployment"
echo "=============================================="

# Get the backend service name from user
echo "Please enter your Render backend service name (e.g., portfolio-backend):"
read -r backend_service_name

if [ -z "$backend_service_name" ]; then
    echo "❌ Backend service name cannot be empty"
    exit 1
fi

# Create the full URL
backend_url="https://${backend_service_name}.onrender.com"

echo "📝 Updating environment.prod.ts with URL: ${backend_url}"

# Update the environment file
cat > src/environments/environment.prod.ts << EOF
export const environment = {
  production: true,
  apiUrl: '${backend_url}/api'
};
EOF

echo "✅ Environment file updated successfully!"
echo ""
echo "📋 Next steps:"
echo "1. Commit and push your changes:"
echo "   git add ."
echo "   git commit -m 'Update API URL for production'"
echo "   git push origin main"
echo ""
echo "2. Redeploy your frontend on Render"
echo ""
echo "3. Test your login/register functionality"
echo ""
echo "🔍 To verify your backend URL is correct, visit:"
echo "   ${backend_url}/actuator/health" 