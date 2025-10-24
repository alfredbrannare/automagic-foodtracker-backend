# Epic: User Authentication

## User Story 1: Register
**As a** user  
**I want to** register an account
**So that** I can use the application with my own data

**Acceptance Criteria:**
- User can register an account with:
  - Username
  - Password
  - Email
- Password is hashed
- JWT token is generated
- Trying to register with an existing username or email shows an appropriate error message

## User Story 2: Login
**As a** user  
**I want to** login to my account
**So that** I can access my personal data

**Acceptance Criteria:**
- User can login with:
  - Username
  - Password
- Password is hashed
- JWT token is generated and returned
- Invalid credentials show an appropriate error message

