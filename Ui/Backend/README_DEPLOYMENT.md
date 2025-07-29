# Backend Deployment Guide for Render.com

## Prerequisites

1. **Database Setup**: You'll need a MySQL database. You can use:
   - Render's MySQL service
   - PlanetScale
   - Railway
   - Any other MySQL provider

2. **Email Configuration**: Set up Gmail App Password for email functionality

## Environment Variables Required

Set these environment variables in your Render.com dashboard:

### Database Configuration
- `DATABASE_URL`: Your MySQL connection string (e.g., `jdbc:mysql://host:port/database_name`)
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password

### Email Configuration
- `GMAIL_USERNAME`: Your Gmail address
- `GMAIL_APP_PASSWORD`: Gmail App Password (not regular password)

### Application Configuration
- `SPRING_PROFILES_ACTIVE`: Set to `production`
- `PORT`: Usually set automatically by Render (default: 8080)

## Deployment Steps

### Option 1: Using render.yaml (Recommended)
1. Push your code to GitHub
2. Connect your repository to Render.com
3. Render will automatically detect the `render.yaml` file
4. Set the environment variables in the Render dashboard
5. Deploy

### Option 2: Manual Configuration
1. Create a new Web Service on Render.com
2. Connect your GitHub repository
3. Set the following:
   - **Environment**: Java
   - **Build Command**: `cd portfolio-microservice && ./mvnw clean package -DskipTests`
   - **Start Command**: `cd portfolio-microservice && java -jar target/portfolio-0.0.1-SNAPSHOT.jar`
4. Add all environment variables
5. Deploy

## Database Setup

### Using Render MySQL
1. Create a new MySQL service on Render
2. Copy the connection details
3. Update your environment variables with the provided connection string

### Using External Database
1. Set up your MySQL database
2. Update the `DATABASE_URL` with your connection string
3. Ensure the database is accessible from Render's servers

## CORS Configuration

The application is configured to allow requests from:
- `https://portfolio-frontend.onrender.com` (your frontend URL)

Update the CORS configuration in `application-production.properties` if your frontend URL changes.

## Health Check

Your application includes Spring Boot Actuator. The health endpoint will be available at:
`https://your-app-name.onrender.com/actuator/health`

## Troubleshooting

1. **Build Failures**: Check the build logs for Maven errors
2. **Database Connection**: Verify your database URL and credentials
3. **Email Issues**: Ensure Gmail App Password is correctly set
4. **CORS Errors**: Update the allowed origins in production properties

## API Endpoints

Your backend API will be available at:
`https://your-app-name.onrender.com/api/`

## Security Notes

- Never commit sensitive information like passwords to your repository
- Use environment variables for all sensitive configuration
- The production profile disables SQL logging for security 