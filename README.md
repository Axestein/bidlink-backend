## Build and Run

Build the application
mvn clean package

Run the application

mvn spring-boot:run -> for development

java -jar target/sme-investor-backend-1.0.0.jar -> for production

## Migration Notes:
Database: MongoDB connection remains the same

Authentication: JWT implementation with Spring Security

Password Hashing: BCrypt instead of bcrypt.js

Error Handling: Spring's ResponseEntity for consistent responses

CORS: Configured in SecurityConfig and individual controllers

Environment Variables: Using Spring's @Value annotation

## Testing the Migration:
Use the same API endpoints as your Node.js application:

POST /api/auth/signup/sme

POST /api/auth/signup/investor

POST /api/auth/login/sme

POST /api/auth/login/investor

POST /api/applications

GET /api/applications

## folder structure 

sme-investor-backend/
├── pom.xml
├── .env
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/smeinvestor/backend/
│   │   │       ├── SmeInvestorApplication.java          // 🎯 MAIN ENTRY POINT
│   │   │       ├── config/
│   │   │       │   ├── SecurityConfig.java              // ⚙️  Security rules
│   │   │       │   └── MongoConfig.java                 // ⚙️  DB configuration
│   │   │       ├── controller/                          // 🎮 API Endpoints
│   │   │       │   ├── AuthController.java              // 👤 Authentication APIs
│   │   │       │   └── ApplicationController.java       // 📋 Application APIs
│   │   │       ├── model/                               // 🗃️  Data Models
│   │   │       │   ├── User.java                        // 👥 User entity
│   │   │       │   ├── Application.java                 // 📄 Application entity
│   │   │       │   └── Role.java                        // 🎭 User roles enum
│   │   │       ├── repository/                          // 💾 Data Access Layer
│   │   │       │   ├── UserRepository.java              // 👤 User DB operations
│   │   │       │   └── ApplicationRepository.java       // 📄 Application DB operations
│   │   │       ├── service/                             // 🧠 Business Logic
│   │   │       │   ├── UserService.java                 // 👤 User operations
│   │   │       │   ├── JwtService.java                  // 🔐 JWT token operations
│   │   │       │   └── ApplicationService.java          // 📄 Application operations
│   │   │       └── security/                            // 🛡️  Security Components
│   │   │           ├── JwtAuthenticationFilter.java     // 🔍 JWT validation filter
│   │   │           └── AuthEntryPoint.java              // 🚫 Auth error handler
│   │   └── resources/
│   │       ├── application.yml                          // ⚙️  Main configuration
│   │       ├── application-dev.yml                      // 🏗️  Dev environment
│   │       └── application-prod.yml                     // 🚀 Prod environment
│   └── test/
└── target/                                              // 🏗️  Build output

<img width="494" height="478" alt="image" src="https://github.com/user-attachments/assets/4ea144e2-5b83-40be-a9b5-4492c01d1acb" />

Detailed Component Flow

1. Request Flow
text
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

2. Authentication Flow
text
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
3. Protected Route Flow
text
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

## Key Configuration Points:

1. Environment Variables Setup
For Development: Use application.yml in src/main/resources/

For Production: Use system environment variables or application-prod.yml

Local Override: Create .env in project root

2. Running with Different Profiles
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev -> for development

mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod -> for production

export SPRING_PROFILES_ACTIVE=prod
mvn spring-boot:run

3. Dependency Injection Flow
text
@SpringBootApplication
    ↓ (auto-scans and creates beans)
@Component, @Service, @Repository, @Controller
    ↓
Dependencies injected via constructor
    ↓
Ready to handle requests
