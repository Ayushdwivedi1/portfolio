# Render Deployment Guide for Portfolio (Frontend + Backend)

This guide will help you deploy both your Angular frontend and Spring Boot backend on Render.

## Prerequisites

1. A Render account
2. A MySQL database (you can use Render's MySQL service or external providers like PlanetScale, Railway, etc.)
3. Gmail account with App Password for email functionality

## Step 1: Set Up Database

1. Create a MySQL database on your preferred provider
2. Note down the database URL, username, and password

## Step 2: Deploy Backend First

1. Go to your Render dashboard
2. Click "New +" and select "Web Service"
3. Connect your GitHub repository
4. Configure the service:
   - **Name**: `portfolio-backend`
   - **Environment**: `Java`
   - **Build Command**: `cd Backend/portfolio-microservice && ./mvnw clean package -DskipTests`
   - **Start Command**: `cd Backend/portfolio-microservice && java -jar target/portfolio-0.0.1-SNAPSHOT.jar`
   - **Java Version**: `17`

5. Add Environment Variables:
   - `SPRING_PROFILES_ACTIVE`: `production`
   - `DATABASE_URL`: Your MySQL database URL
   - `DATABASE_USERNAME`: Your database username
   - `DATABASE_PASSWORD`: Your database password
   - `GMAIL_USERNAME`: Your Gmail address
   - `GMAIL_APP_PASSWORD`: Your Gmail app password

6. Click "Create Web Service"

## Step 3: Deploy Frontend

1. Go to your Render dashboard
2. Click "New +" and select "Static Site"
3. Connect your GitHub repository
4. Configure the service:
   - **Name**: `portfolio-frontend`
   - **Build Command**: `npm install && npm run build:prod`
   - **Publish Directory**: `dist/portfolio/browser`

5. Add Environment Variables:
   - `NODE_VERSION`: `20`

6. Click "Create Static Site"

## Step 4: Update CORS Configuration

After deploying the backend, update the CORS configuration in `application-production.properties`:

```properties
spring.web.cors.allowed-origins=https://your-frontend-service-name.onrender.com
```

## Step 5: Test Your Deployment

1. Test your backend API endpoints
2. Test your frontend application
3. Verify that frontend can communicate with backend

## Environment Variables Reference

### Backend Environment Variables:
- `SPRING_PROFILES_ACTIVE`: Set to `production`
- `DATABASE_URL`: MySQL connection string
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password
- `GMAIL_USERNAME`: Gmail address for sending emails
- `GMAIL_APP_PASSWORD`: Gmail app password

### Frontend Environment Variables:
- `NODE_VERSION`: Set to `20`

## Troubleshooting

### Common Issues:

1. **Build Failures**:
   - Check if all dependencies are properly configured
   - Verify Maven wrapper permissions
   - Ensure Node.js version compatibility

2. **Database Connection Issues**:
   - Verify database credentials
   - Check if database is accessible from Render
   - Ensure database URL format is correct

3. **CORS Issues**:
   - Update CORS configuration with correct frontend URL
   - Check if backend is properly configured for CORS

4. **Email Service Issues**:
   - Verify Gmail app password is correct
   - Check if 2FA is enabled on Gmail account
   - Ensure app password has proper permissions

### API Connection Issues (localhost:8080 Error)

If you see errors like `Http failure response for http://localhost:8080/api/auth/register: 0 Unknown Error`, this means your frontend is still using the development environment instead of production.

**Solution:**

1. **Find your backend URL**:
   - Go to your Render dashboard
   - Find your backend service
   - Copy the URL (e.g., `https://portfolio-backend.onrender.com`)

2. **Update the API URL**:
   ```bash
   cd Ui
   ./update-api-url.sh
   ```
   - Enter your backend service name when prompted
   - The script will update `src/environments/environment.prod.ts`

3. **Redeploy your frontend**:
   ```bash
   git add .
   git commit -m "Update API URL for production"
   git push origin main
   ```

4. **Verify the build is using production**:
   - Check that `npm run build:prod` is being used
   - Ensure the build output uses the production environment

5. **Clear browser cache**:
   - Hard refresh your browser (Ctrl+F5 or Cmd+Shift+R)
   - Or clear browser cache completely

**Manual Fix:**
If the script doesn't work, manually update `src/environments/environment.prod.ts`:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://your-backend-service-name.onrender.com/api'
};
```

## Security Notes

1. Never commit sensitive information like passwords to your repository
2. Use environment variables for all sensitive configuration
3. Regularly rotate your Gmail app password
4. Use HTTPS for all production communications

## Monitoring

1. Use Render's built-in logging to monitor your applications
2. Set up health checks for your backend service
3. Monitor database connections and performance
4. Set up alerts for service downtime

## Cost Optimization

1. Use Render's free tier for development
2. Scale up only when needed
3. Monitor usage to avoid unexpected charges
4. Consider using external database providers for better pricing 