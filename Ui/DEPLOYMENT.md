# Deployment Guide for Netlify

## Prerequisites
- Node.js 18 or higher
- npm or yarn package manager
- Netlify account

## Deployment Steps

### 1. Build the Application
```bash
npm run build
```

### 2. Deploy to Netlify

#### Option A: Deploy via Netlify UI
1. Go to [Netlify](https://netlify.com) and sign in
2. Click "New site from Git" or "Add new site" > "Deploy manually"
3. If deploying manually, drag and drop the `dist/portfolio/browser` folder
4. If using Git, connect your repository and set:
   - Build command: `cd Ui && npm install && npm run build`
   - Publish directory: `Ui/dist/portfolio/browser`

#### Option B: Deploy via Netlify CLI
1. Install Netlify CLI: `npm install -g netlify-cli`
2. Login: `netlify login`
3. Deploy: `netlify deploy --prod --dir=Ui/dist/portfolio/browser`

### 3. Configuration Files

The following files are already configured for Netlify deployment:

- `public/_redirects` - Handles Angular routing
- `netlify.toml` - Build configuration
- `src/index.html` - Base href set to "/"

### 4. Environment Variables (if needed)

If you need to set environment variables:
1. Go to Site settings > Environment variables in Netlify
2. Add any required environment variables

### 5. Custom Domain (Optional)

1. Go to Site settings > Domain management
2. Add your custom domain
3. Configure DNS settings as instructed by Netlify

## Troubleshooting

### 404 Errors
- Ensure `public/_redirects` file is present
- Check that `netlify.toml` is in the root directory
- Verify build output directory is `Ui/dist/portfolio/browser`

### Build Failures
- Check Node.js version (should be 18+)
- Ensure all dependencies are installed: `npm install`
- Check build logs in Netlify dashboard

### Routing Issues
- The `_redirects` file should handle all routing
- If issues persist, check the base href in `index.html`

## File Structure for Deployment

```
portfolio/
├── Ui/                     # Angular application
│   ├── public/
│   │   ├── _redirects      # Netlify redirects
│   │   └── assets/         # Static assets
│   ├── src/
│   │   ├── index.html      # Main HTML file
│   │   └── ...             # Angular source files
│   ├── netlify.toml        # Netlify configuration
│   ├── angular.json        # Angular configuration
│   └── package.json        # Dependencies and scripts
└── ...                     # Other project files
```

## Build Output

After running `cd Ui && npm run build`, the deployable files will be in:
`Ui/dist/portfolio/browser/`

This directory contains:
- `index.html` - Main entry point
- `assets/` - Static assets
- `_redirects` - Netlify redirects (copied from public/)
- Other build artifacts 