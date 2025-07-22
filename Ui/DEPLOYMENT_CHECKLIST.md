# Pre-Deployment Checklist for Render

## ✅ Completed Fixes

- [x] Fixed incorrect paths in `package.json` (changed `Ui/` to `Ui`)
- [x] Fixed incorrect paths in `render.yaml` (changed `./Ui/dist/browser` to `./Ui/dist/browser`)
- [x] Fixed incorrect paths in `build.sh` (changed `cd Ui/` to `cd Ui`)
- [x] Fixed incorrect paths in `RENDER_DEPLOYMENT.md`
- [x] Created environment configuration files
- [x] Updated auth service to use environment variables
- [x] Verified build works locally
- [x] Created comprehensive deployment guide

## 🔧 Required Actions Before Deployment

### 1. Update Production API URL
**File**: `src/environments/environment.prod.ts`

Replace the placeholder with your actual backend API URL:
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://your-backend-domain.com/api' // ⚠️ UPDATE THIS
};
```

**Options:**
- If your backend is on Render: Use the Render-provided URL
- If your backend is elsewhere: Use your domain
- If no backend: Remove API calls or set to empty string

### 2. Test Production Build Locally
```bash
cd Ui
npm run build:prod
```
Verify the build completes without errors.

### 3. Commit and Push Changes
```bash
git add .
git commit -m "Fix deployment paths and add environment configuration"
git push origin main
```

## 🚀 Deployment Steps

### Option 1: Using render.yaml (Recommended)
1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" → "Blueprint"
3. Connect your Git repository
4. Render will automatically detect `render.yaml`
5. Follow the prompts to deploy

### Option 2: Manual Configuration
1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" → "Static Site"
3. Connect your Git repository
4. Configure:
   - **Build Command**: `cd Ui && npm install && npm run build:prod`
   - **Publish Directory**: `Ui/dist/portfolio/browser`
   - **Environment Variable**: `NODE_VERSION` = `20`

## 🌐 Custom Domain Setup

### 1. Add Domain in Render
- Go to your static site in Render dashboard
- Settings → Custom Domains
- Add your domain

### 2. Configure DNS Records
**For apex domain (yourdomain.com):**
- Type: `A`
- Name: `@`
- Value: `76.76.19.19`

**For www subdomain:**
- Type: `CNAME`
- Name: `www`
- Value: `your-render-app.onrender.com`

## 🔍 Post-Deployment Verification

- [ ] Site loads without errors
- [ ] All routes work (no 404 errors)
- [ ] Assets load correctly
- [ ] API calls work (if applicable)
- [ ] SSL certificate is active
- [ ] Custom domain works (if configured)

## 📁 File Structure Summary

```
portfolio/
├── Ui/                          # Angular application
│   ├── src/
│   │   ├── environments/        # ✅ Environment configs
│   │   │   ├── environment.ts   # Development
│   │   │   └── environment.prod.ts # Production
│   │   └── app/
│   │       └── services/
│   │           └── auth.service.ts # ✅ Updated for environments
│   ├── angular.json
│   └── package.json
├── render.yaml                  # ✅ Fixed paths
├── package.json                 # ✅ Fixed paths
├── build.sh                     # ✅ Fixed paths
├── RENDER_DEPLOYMENT.md         # ✅ Updated
├── RENDER_DEPLOYMENT_GUIDE.md   # ✅ New comprehensive guide
└── DEPLOYMENT_CHECKLIST.md      # ✅ This file
```

## 🆘 Troubleshooting

If deployment fails:
1. Check build logs in Render dashboard
2. Verify all paths are correct
3. Ensure Node.js version is 20
4. Check that all dependencies are in package.json
5. Verify environment variables are set correctly

## 📞 Support

- Render Documentation: https://render.com/docs
- Render Community: https://community.render.com
- Angular Documentation: https://angular.io/docs 