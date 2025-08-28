# Email Setup Guide for Email Verification Application

## 🚀 Quick Start Options

### Option 1: Development Mode (No Email) - RECOMMENDED FOR TESTING
```bash
# Run without email functionality
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Option 2: Full Email Functionality
```bash
# Run with email functionality (requires Gmail setup)
mvn spring-boot:run
```

## 📧 Gmail SMTP Setup

### Step 1: Enable 2-Factor Authentication
1. Go to [Google Account Settings](https://myaccount.google.com/)
2. Navigate to Security → 2-Step Verification
3. Enable 2-Factor Authentication if not already enabled

### Step 2: Generate App Password
1. Go to [App Passwords](https://myaccount.google.com/apppasswords)
2. Select "Mail" as the app
3. Select "Other (Custom name)" as device
4. Enter a name (e.g., "Email Verification App")
5. Click "Generate"
6. **Copy the 16-character password** (e.g., `abcd efgh ijkl mnop`)

### Step 3: Configure Application

#### Method A: Environment Variables (Recommended)
```bash
export MAIL_USERNAME=your-email@gmail.com
export MAIL_PASSWORD=your-16-character-app-password
mvn spring-boot:run
```

#### Method B: Update application.properties
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-16-character-app-password
```

#### Method C: Create .env file
```bash
# Copy env-template.txt to .env
cp env-template.txt .env

# Edit .env with your credentials
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-16-character-app-password
```

## 🔧 Profile Configuration

### Development Profile (No Email)
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```
- Uses H2 in-memory database
- Email functionality disabled
- Perfect for testing without email setup

### Default Profile (Email Enabled)
```bash
mvn spring-boot:run
```
- Uses H2 in-memory database
- Email functionality enabled (if configured)
- Requires Gmail credentials

### Production Profile
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```
- Uses MySQL database
- Email functionality enabled
- Requires all environment variables

## 🧪 Testing Email Functionality

### 1. Test User Registration
```bash
curl -X POST http://localhost:8082/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "your-email@gmail.com",
    "password": "password123"
  }'
```

### 2. Check Email for Verification Code
- Look for email from your application
- Copy the verification code

### 3. Verify Account
```bash
curl -X POST http://localhost:8082/auth/verify \
  -H "Content-Type: application/json" \
  -d '{
    "email": "your-email@gmail.com",
    "verificationCode": "123456"
  }'
```

## 🚨 Troubleshooting

### Common Issues

#### 1. "Authentication failed" Error
- **Cause**: Using regular Gmail password instead of App Password
- **Solution**: Generate and use App Password

#### 2. "Username and Password not accepted"
- **Cause**: Incorrect credentials or 2FA not enabled
- **Solution**: 
  - Enable 2-Factor Authentication
  - Generate new App Password
  - Double-check username (email)

#### 3. "Connection timeout"
- **Cause**: Firewall or network issues
- **Solution**: Check network connectivity to smtp.gmail.com:587

#### 4. "Less secure app access"
- **Cause**: Using deprecated Gmail setting
- **Solution**: Use App Passwords instead

### Debug Mode
Enable detailed logging by adding to application.properties:
```properties
logging.level.org.springframework.mail=DEBUG
logging.level.com.sun.mail=DEBUG
```

## 📱 Alternative Email Providers

### Outlook/Hotmail
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
```

### Yahoo
```properties
spring.mail.host=smtp.mail.yahoo.com
spring.mail.port=587
```

### Custom SMTP Server
```properties
spring.mail.host=your-smtp-server.com
spring.mail.port=587
spring.mail.username=your-username
spring.mail.password=your-password
```

## 🔒 Security Notes

- **Never commit email passwords to version control**
- **Use environment variables for production**
- **App Passwords are more secure than regular passwords**
- **Consider using OAuth2 for production applications**

## 📞 Support

If you encounter issues:
1. Check the application logs for detailed error messages
2. Verify your Gmail App Password is correct
3. Ensure 2-Factor Authentication is enabled
4. Test SMTP connection manually if needed
