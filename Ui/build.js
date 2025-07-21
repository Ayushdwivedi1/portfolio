#!/usr/bin/env node

const { execSync } = require('child_process');
const path = require('path');

console.log('🚀 Starting build process...');
console.log('Current directory:', process.cwd());

try {
  // Check Node.js version
  const nodeVersion = execSync('node -v', { encoding: 'utf8' }).trim();
  console.log('Node.js version:', nodeVersion);
  
  // Check npm version
  const npmVersion = execSync('npm -v', { encoding: 'utf8' }).trim();
  console.log('npm version:', npmVersion);
  
  // Navigate to portfolio directory
  const portfolioPath = path.join(process.cwd(), 'portfolio');
  console.log('Navigating to:', portfolioPath);
  process.chdir(portfolioPath);
  
  // Install dependencies
  console.log('📦 Installing dependencies...');
  execSync('npm ci', { stdio: 'inherit' });
  
  // Build the project
  console.log('🔨 Building Angular project...');
  execSync('npm run build', { stdio: 'inherit' });
  
  console.log('✅ Build completed successfully!');
  
} catch (error) {
  console.error('❌ Build failed:', error.message);
  process.exit(1);
} 