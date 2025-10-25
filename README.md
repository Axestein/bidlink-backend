# Bidlink - Spring Boot Backend Migration

## Project Overview
Migration of SME Investor Platform backend from Node.js to Spring Boot Java with full feature parity including JWT authentication, MongoDB integration, and RESTful APIs.

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+
- Git

### Installation & Running

#### 1. Clone and Setup
```bash
git clone Axestein/bidlink-backend
cd sme-investor-backend
```

#### 2. Build the Application
```bash
mvn clean package
```

#### 3. Run the Application

**Development Mode:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

**Production Mode:**
```bash
java -jar target/sme-investor-backend-1.0.0.jar --spring.profiles.active=prod
```

**Alternative Production Run:**
```bash
export SPRING_PROFILES_ACTIVE=prod
mvn spring-boot:run
```

## Project Structure

```
sme-investor-backend/
├── pom.xml
├── .env
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/smeinvestor/backend/
│   │   │       ├── SmeInvestorApplication.java          // MAIN ENTRY POINT
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java              // Security rules
│   │   │       │   └── MongoConfig.java                 // DB configuration
│   │   │       ├── controller/                          // API Endpoints
│   │   │       │   ├── AuthController.java              // Authentication APIs
│   │   │       │   └── ApplicationController.java       // Application APIs
│   │   │       ├── model/                               // Data Models
│   │   │       │   ├── User.java                        // User entity
│   │   │       │   ├── Application.java                 // Application entity
│   │   │       │   └── Role.java                        // User roles enum
│   │   │       ├── repository/                          // Data Access Layer
│   │   │       │   ├── UserRepository.java              // User DB operations
│   │   │       │   └── ApplicationRepository.java       // Application DB operations
│   │   │       ├── service/                             // Business Logic
│   │   │       │   ├── UserService.java                 // User operations
│   │   │       │   ├── JwtService.java                  // JWT token operations
│   │   │       │   └── ApplicationService.java          // Application operations
│   │   │       └── security/                            // Security Components
│   │   │           ├── JwtAuthenticationFilter.java     // JWT validation filter
│   │   │           └── AuthEntryPoint.java              // Auth error handler
│   │   └── resources/
│   │       ├── application.yml                          // Main configuration
│   │       ├── application-dev.yml                      // Dev environment
│   │       └── application-prod.yml                     // Prod environment
│   └── test/
└── target/                                              // Build output
```

## Configuration

### Environment Variables Setup

**For Development:** Use `application.yml` in `src/main/resources/`

**For Production:** Use system environment variables or `application-prod.yml`

**Local Override:** Create `.env` in project root

### Configuration Files

#### Main Configuration (`src/main/resources/application.yml`)
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGO_URI:mongodb://localhost:27017/sme_investor_db}
      database: sme_investor_db
  
  security:
    jwt:
      secret: ${JWT_SECRET:your-jwt-secret-here}
      expiration: 3600000

server:
  port: ${PORT:5000}

logging:
  level:
    com.smeinvestor: DEBUG
```

#### Environment Variables (`.env`)
```env
MONGO_URI=mongodb://localhost:27017/sme_investor_db
JWT_SECRET=your-jwt-secret-key-here
PORT=5000
```

## Migration Notes

| Component | Node.js | Spring Boot |
|-----------|---------|-------------|
| **Database** | Mongoose | Spring Data MongoDB |
| **Authentication** | JWT + bcrypt | Spring Security + JWT + BCrypt |
| **Password Hashing** | bcrypt.js | BCryptPasswordEncoder |
| **Error Handling** | Custom middleware | ResponseEntity + ExceptionHandler |
| **CORS** | cors package | SecurityConfig configuration |
| **Environment Variables** | dotenv package | @Value annotation + application.yml |
| **API Routes** | Express Router | Spring MVC @RestController |

## Testing the Migration

Use the same API endpoints as your Node.js application:

### Authentication Endpoints
- **POST** `/api/auth/signup/sme` - SME registration
- **POST** `/api/auth/signup/investor` - Investor registration  
- **POST** `/api/auth/login/sme` - SME login
- **POST** `/api/auth/login/investor` - Investor login

### Application Endpoints
- **POST** `/api/applications` - Create new application
- **GET** `/api/applications` - Fetch all applications

### Example API Calls

#### User Registration
```bash
curl -X POST http://localhost:5000/api/auth/signup/sme \
  -H "Content-Type: application/json" \
  -d '{"email": "sme@example.com", "password": "password123"}'
```

#### User Login
```bash
curl -X POST http://localhost:5000/api/auth/login/sme \
  -H "Content-Type: application/json" \
  -d '{"email": "sme@example.com", "password": "password123"}'
```

#### Create Application (Protected)
```bash
curl -X POST http://localhost:5000/api/applications \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "amount": 50000,
    "purpose": "Business Expansion",
    "companyName": "Tech Solutions Ltd",
    "ownerName": "John Doe",
    "yearsInOperation": 3,
    "annualRevenue": 200000,
    "contactNumber": "+1234567890",
    "businessType": "Technology",
    "location": "New York"
  }'
```

#### Get Applications (Protected)
```bash
curl -X GET http://localhost:5000/api/applications \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Architecture & Flow

<img width="494" height="478" alt="image" src="https://github.com/user-attachments/assets/4ea144e2-5b83-40be-a9b5-4492c01d1acb" />

### System Architecture Diagram

```
┌─────────────────┐    HTTP Requests    ┌─────────────────┐
│   Client        │ ──────────────────► │   Controller    │
│  (Frontend)     │                     │   Layer         │
└─────────────────┘    JSON Responses   └─────────────────┘
                                              │
                                              │ Service Calls
                                              ▼
┌─────────────────┐                     ┌─────────────────┐
│  Security       │                     │   Service       │
│   Layer         │                     │   Layer         │
│ • JWT Filter    │ ◄─────────────────► │ • Business Logic│
│ • Auth Manager  │    Authentication   │ • Validation    │
└─────────────────┘                     └─────────────────┘
                                              │
                                              │ Repository Calls
                                              ▼
┌─────────────────┐                     ┌─────────────────┐
│   Database      │ ◄─────────────────► │  Repository     │
│   (MongoDB)     │    CRUD Operations  │   Layer         │
└─────────────────┘                     └─────────────────┘
```

### Detailed Component Flow

#### 1. Request Flow
```
Client Request 
    ↓
JwtAuthenticationFilter (checks Authorization header)
    ↓
SecurityConfig (routes to appropriate endpoints)
    ↓
Controller (handles HTTP methods)
    ↓
Service (business logic)
    ↓
Repository (database operations)
    ↓
MongoDB
```

#### 2. Authentication Flow
```
POST /api/auth/login/sme
    ↓
AuthController.loginSme()
    ↓
AuthenticationManager.authenticate()
    ↓
UserService.loadUserByUsername()
    ↓
UserRepository.findByEmail()
    ↓
JwtService.generateToken()
    ↓
Return JWT to client
```

#### 3. Protected Route Flow
```
GET /api/applications (with Bearer token)
    ↓
JwtAuthenticationFilter.extractToken()
    ↓
JwtService.validateToken()
    ↓
UserService.loadUserByUsername()
    ↓
SecurityContext.setAuthentication()
    ↓
ApplicationController.getAllApplications()
    ↓
ApplicationService.getAllApplications()
    ↓
ApplicationRepository.findAll()
    ↓
Return applications data
```

### 4. Dependency Injection Flow
```
@SpringBootApplication
    ↓ (auto-scans and creates beans)
@Component, @Service, @Repository, @Controller
    ↓
Dependencies injected via constructor
    ↓
Ready to handle requests
```

## Development

### Adding New Features

1. **New Entity**: Create model class in `model/` package
2. **Database Operations**: Create repository interface in `repository/` package  
3. **Business Logic**: Create service class in `service/` package
4. **API Endpoints**: Create controller in `controller/` package
5. **Security**: Update `SecurityConfig.java` if needed

### Code Style
- Use Spring Boot conventions
- Follow Java naming conventions
- Use constructor injection for dependencies
- Implement proper exception handling
- Add comprehensive logging

## Monitoring & Logging

The application includes structured logging:

```java
private static final Logger logger = LoggerFactory.getLogger(YourClass.class);

// Usage
logger.info("User registered successfully: {}", user.getEmail());
logger.error("Error creating application: {}", exception.getMessage());
```

## Security Features

- JWT-based authentication
- Password hashing with BCrypt
- CORS configuration
- Role-based access control
- CSRF protection disabled for API (stateless)
- Secure headers configuration

## Troubleshooting

### Common Issues

1. **MongoDB Connection Failed**
   - Check if MongoDB is running
   - Verify connection string in configuration
   - Check network connectivity

2. **JWT Token Issues**
   - Verify JWT secret matches
   - Check token expiration
   - Validate token format

3. **Port Already in Use**
   - Change port in configuration
   - Kill existing process using the port

4. **Build Failures**
   - Clear Maven cache: `mvn clean`
   - Check Java version compatibility
   - Verify all dependencies in pom.xml

### Logs Location
- Development: Console output
- Production: Check application logs based on logging configuration

## Performance Considerations

- Spring Boot embedded Tomcat server
- Connection pooling for MongoDB
- JWT stateless authentication
- Efficient dependency injection
- Optimized database queries with Spring Data

## Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is licensed under the MIT License.

---

**Note**: This migration maintains full API compatibility with the original Node.js backend, ensuring seamless transition for frontend clients.
