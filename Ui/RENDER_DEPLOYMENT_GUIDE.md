# Render Deployment Guide for Portfolio

## Prerequisites
- Node.js 20 or higher
- npm package manager
- Render account
- Git repository with your code

## Quick Deployment Steps

### 1. Connect to Render
1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" and select "Static Site"
3. Connect your Git repository
4. Select the repository containing your portfolio

### 2. Configure Build Settings
Use these exact settings in the Render dashboard:

- **Name**: `portfolio-angular` (or your preferred name)
- **Build Command**: `cd Ui && npm install && npm run build`
- **Publish Directory**: `Ui/dist/portfolio/browser`
- **Environment**: Static Site

### 3. Environment Variables
Add these environment variables in Render dashboard:
- `NODE_VERSION`: `20`

### 4. Deploy
1. Click "Create Static Site"
2. Render will automatically build and deploy your application
3. Your site will be available at the provided URL

## Alternative: Using render.yaml (Recommended)

The project includes a `render.yaml` file for automated deployment:

1. In Render dashboard, select "Blueprint" instead of "Static Site"
2. Render will automatically detect and use the `render.yaml` configuration
3. Follow the prompts to deploy

## Custom Domain Setup

### 1. Add Custom Domain in Render
1. In your Render dashboard, go to your static site
2. Click "Settings" → "Custom Domains"
3. Add your domain (e.g., `yourdomain.com`)

### 2. Configure DNS
Render will provide you with DNS records to add to your domain provider:

**For apex domain (yourdomain.com):**
- Type: `A`
- Name: `@`
- Value: `76.76.19.19`

**For www subdomain (www.yourdomain.com):**
- Type: `CNAME`
- Name: `www`
- Value: `your-render-app.onrender.com`

### 3. SSL Certificate
- Render automatically provides SSL certificates
- Wait 24-48 hours for SSL to be fully active

## Environment Configuration

### Backend API Configuration
Before deploying, update the production API URL:

1. Edit `src/environments/environment.prod.ts`:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://your-backend-domain.com/api' // Replace with your actual backend URL
};
```

2. If your backend is also on Render, use the Render-provided URL

### Environment Variables for Different Environments
You can add environment variables in the Render dashboard for:
- API endpoints
- Feature flags
- Analytics keys
- Other configuration

## File Structure
```
portfolio/
├── Ui/                     # Angular application
│   ├── src/
│   │   ├── environments/   # Environment configurations
│   │   │   ├── environment.ts
│   │   │   └── environment.prod.ts
│   │   └── app/
│   ├── angular.json
│   └── package.json
├── render.yaml             # Render configuration
├── package.json            # Root package.json
└── build.sh               # Build script
```

## Troubleshooting

### Build Failures
1. **Node version issues**: Ensure NODE_VERSION is set to 20
2. **Path issues**: Verify build command uses `cd Ui` not `cd Ui`
3. **Dependencies**: Check that all dependencies are in package.json

### 404 Errors on Routes
- The `render.yaml` includes SPA routing configuration
- All routes should redirect to `index.html`

### Assets Not Loading
- Verify `publishDirectory` points to `Ui/dist/portfolio/browser`
- Check that assets are included in the build output

### API Connection Issues
- Ensure production API URL is correctly set in `environment.prod.ts`
- Check CORS settings on your backend
- Verify SSL certificates are valid

## Monitoring and Maintenance

### Build Logs
- View detailed build logs in the Render dashboard
- Set up notifications for build failures

### Performance
- Monitor performance using Render's built-in analytics
- Check Core Web Vitals in Google PageSpeed Insights

### Updates
- Push changes to your Git repository
- Render will automatically rebuild and deploy
- Monitor build status in the dashboard

## Security Considerations

1. **Environment Variables**: Never commit sensitive data to Git
2. **API Keys**: Store API keys as environment variables in Render
3. **HTTPS**: Render provides automatic SSL certificates
4. **CORS**: Configure your backend to allow requests from your domain

## Cost Optimization

- Render's static site hosting is free for basic usage
- Monitor usage in the dashboard
- Consider paid plans for additional features or higher limits

## Support

- Render Documentation: https://render.com/docs
- Render Community: https://community.render.com
- Angular Documentation: https://angular.io/docs 