# Deploying Portfolio to Render

This guide will help you deploy your Angular portfolio application to Render.

## Prerequisites

1. A Render account (free tier available)
2. Your code pushed to a Git repository (GitHub, GitLab, etc.)

## Deployment Steps

### 1. Connect Your Repository

1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" and select "Static Site"
3. Connect your Git repository
4. Select the repository containing your portfolio

### 2. Configure Build Settings

Use these settings in the Render dashboard:

- **Name**: `portfolio-angular` (or any name you prefer)
- **Build Command**: `cd Ui/portfolio && npm install && npm run build:prod`
- **Publish Directory**: `Ui/portfolio/dist/portfolio/browser`
- **Environment**: Static Site

### 3. Environment Variables

Add these environment variables:
- `NODE_VERSION`: `20`

### 4. Deploy

1. Click "Create Static Site"
2. Render will automatically build and deploy your application
3. Your site will be available at the provided URL

## Alternative: Using render.yaml

If you prefer to use the `render.yaml` file (already created in the root):

1. In Render dashboard, select "Blueprint" instead of "Static Site"
2. Render will automatically detect and use the `render.yaml` configuration
3. Follow the prompts to deploy

## Troubleshooting

### Common Issues

1. **Build fails with npm errors**: Ensure you're using Node.js version 20 or higher
2. **404 errors on routes**: The `render.yaml` includes SPA routing configuration to handle Angular routes
3. **Assets not loading**: Check that the `publishDirectory` points to the correct build output

### Build Output Location

The Angular build outputs to: `Ui/portfolio/dist/portfolio/browser`

This is configured in the `angular.json` file and matches the `staticPublishPath` in `render.yaml`.

## Custom Domain (Optional)

1. In your Render dashboard, go to your static site
2. Click "Settings" → "Custom Domains"
3. Add your domain and follow the DNS configuration instructions

## Environment Variables for Different Environments

You can add environment variables in the Render dashboard for:
- API endpoints
- Feature flags
- Analytics keys
- Other configuration

## Monitoring

- View build logs in the Render dashboard
- Set up notifications for build failures
- Monitor performance using Render's built-in analytics 