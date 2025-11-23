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

---

## User Story 3: Update nutritional goals
**As a** user
**I want to** update my nutritional goals
**So that** I can adjust them as my health goals change

**Acceptance Criteria:**
- User can modify goal values
- Changes are saved immediately

---

## User Story 4: See progress towards goals
**As a** user
**I want to** see how my daily nutrition compares to my goals
**So that** I know if I'm on track

**Acceptance Criteria:**
- User sees comparison for each macro (protein, carbs, fat, calories)
- Progress is shown as absolute numbers and percentage
- User can see if they're over, under, or on target

---

## User Story 5: Delete my account
**As a** user
**I want to** delete my account
**So that** I can remove all my data from the system

**Acceptance Criteria:**
- User can initiate account deletion
- System requires confirmation
- All user data is removed (profile, meals, storage items)
