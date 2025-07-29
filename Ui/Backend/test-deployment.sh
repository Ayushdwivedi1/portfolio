#!/bin/bash

echo "Testing Portfolio Backend Deployment..."
echo "======================================"

# Test 1: Health Check
echo "1. Testing Health Endpoint..."
curl -I https://portfolio-backend.onrender.com/actuator/health
echo ""

# Test 2: API Base URL
echo "2. Testing API Base URL..."
curl -I https://portfolio-backend.onrender.com/api
echo ""

# Test 3: Auth Endpoint
echo "3. Testing Auth Endpoint..."
curl -I https://portfolio-backend.onrender.com/api/auth
echo ""

# Test 4: CORS Headers
echo "4. Testing CORS Headers..."
curl -H "Origin: https://portfolio-frontend.onrender.com" \
     -H "Access-Control-Request-Method: POST" \
     -H "Access-Control-Request-Headers: Content-Type" \
     -X OPTIONS \
     -I https://portfolio-backend.onrender.com/api/auth/login
echo ""

echo "Deployment test completed!" 