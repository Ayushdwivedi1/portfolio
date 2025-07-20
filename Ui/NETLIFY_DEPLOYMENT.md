# Netlify Deployment Guide - Fixed Configuration

## Problem Solved
The previous deployment was failing because:
1. Netlify was looking in the wrong directory (`Ui` instead of `Ui/portfolio`)
2. Angular CLI (`ng`) was not available in the build environment
3. Build command was incorrect

## Solution Implemented

### 1. Root-Level Configuration Files
Created these files at the root level (`Ui/` directory):

#### `netlify.toml`
```toml
[build]
  publish = "portfolio/dist/portfolio/browser"
  command = "npm run build"

[build.environment]
  NODE_VERSION = "18"

[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200
```

#### `package.json`
```json
{
  "name": "portfolio-deployment",
  "version": "1.0.0",
  "description": "Deployment configuration for Ayush Dwivedi's portfolio",
  "scripts": {
    "build": "cd portfolio && npm install && npm run build",
    "postbuild": "echo 'Build completed successfully!'"
  },
  "private": true
}
```

#### `public/_redirects`
```
/*    /index.html   200
```

### 2. Netlify Dashboard Settings

In your Netlify dashboard, set these build settings:

**Build settings:**
- **Build command:** `npm run build`
- **Publish directory:** `portfolio/dist/portfolio/browser`
- **Base directory:** (leave empty)

**Environment variables:**
- `NODE_VERSION`: `20`

### 3. Deployment Steps

1. **Push your code to Git** (if using Git deployment)
2. **Or manually deploy:**
   - Run `npm run build` locally
   - Upload the `portfolio/dist/portfolio/browser` folder to Netlify

### 4. File Structure
```
Ui/
├── netlify.toml              # Netlify configuration
├── package.json              # Root build script
├── public/
│   └── _redirects            # Routing configuration
└── portfolio/                # Angular project
    ├── src/
    ├── public/
    │   └── _redirects        # Also copied to build output
    ├── angular.json
    ├── package.json
    └── dist/portfolio/browser/  # Build output
```

### 5. What Each File Does

- **`netlify.toml`**: Tells Netlify where to find the build output and how to handle routing
- **`package.json`**: Provides a build script that installs dependencies and builds the Angular app
- **`_redirects`**: Ensures all routes serve `index.html` for Angular's client-side routing
- **`portfolio/public/_redirects`**: Gets copied to the build output for routing

### 6. Testing the Build Locally

Before deploying, test the build process:

```bash
# From the Ui directory
npm run build

# Check the output
ls -la portfolio/dist/portfolio/browser/
```

You should see:
- `index.html`
- `_redirects`
- `assets/`
- Other build files

### 7. Troubleshooting

If you still get build errors:

1. **Check Node.js version**: Ensure you're using Node.js 20+
2. **Clear cache**: In Netlify dashboard, go to Site settings > Build & deploy > Clear cache
3. **Check build logs**: Look for specific error messages in the build output
4. **Verify file paths**: Ensure all configuration files are in the correct locations

### 8. Alternative Deployment Method

If the above doesn't work, you can also:

1. Build locally: `cd portfolio && npm run build`
2. Upload the `portfolio/dist/portfolio/browser` folder directly to Netlify
3. Set the publish directory to the uploaded folder

This approach bypasses the build process on Netlify entirely.

## Expected Result

After successful deployment:
- ✅ No more 404 errors
- ✅ Navigation works properly
- ✅ All routes serve the Angular app
- ✅ Smooth scrolling between sections works 