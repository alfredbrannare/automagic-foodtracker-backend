# Use Cases – User Authentication & Profile

---

## Use Case 1: Register Account
**Actor:** User (via Frontend)  
**Scenario:** New user creates an account  
**Trigger:** User submits registration form

**Preconditions:**
- Username and email are not already registered

**Main Flow:**
1. Frontend sends POST /api/auth/register with username, email, password
2. Backend validates input (username length, email format, password requirements)
3. Backend checks username doesn't exist
4. Backend checks email doesn't exist
5. Backend hashes password using BCrypt
6. Backend creates user with default goals (150g protein, 250g carbs, 60g fat, 2000 kcal)
7. Backend generates JWT access and refresh tokens
8. Backend returns tokens in HTTP-only cookies
9. Frontend redirects to dashboard

**Postconditions:**
- User account is created
- User is logged in with JWT tokens
- Default nutritional goals are set

**Alternative Flows:**
- Username already exists → return 409 Conflict
- Email already exists → return 409 Conflict
- Invalid input → return 400 Bad Request

---

## Use Case 2: Login
**Actor:** User (via Frontend)  
**Scenario:** Existing user logs into their account  
**Trigger:** User submits login form

**Preconditions:**
- User account exists

**Main Flow:**
1. Frontend sends POST /api/auth/login with username and password
2. Backend validates input
3. Backend finds user by username
4. Backend verifies password using BCrypt
5. Backend generates JWT access and refresh tokens
6. Backend returns tokens in HTTP-only cookies
7. Frontend redirects to dashboard

**Postconditions:**
- User is authenticated
- JWT tokens are stored in cookies
- User can access protected routes

**Alternative Flows:**
- User not found → return 401 Unauthorized
- Incorrect password → return 401 Unauthorized
- Invalid input → return 400 Bad Request

---

## Use Case 3: Logout
**Actor:** User (via Frontend)  
**Scenario:** User logs out of their account  
**Trigger:** User clicks logout button

**Preconditions:**
- User is authenticated

**Main Flow:**
1. Frontend sends POST /api/auth/logout
2. Backend clears JWT tokens (sets expired cookies)
3. Frontend redirects to login page

**Postconditions:**
- JWT tokens are invalidated
- User cannot access protected routes
- User must log in again

**Alternative Flows:**
- None (logout always succeeds)

---

## Use Case 4: Delete Account
**Actor:** User (via Frontend)  
**Scenario:** User permanently deletes their account  
**Trigger:** User confirms account deletion

**Preconditions:**
- User is authenticated
- User has confirmed deletion

**Main Flow:**
1. Frontend sends DELETE /api/auth (authenticated request)
2. Backend validates JWT token
3. Backend deletes all user data:
    - All meals
    - All storage items
    - User profile
4. Backend clears JWT tokens
5. Backend returns 204 No Content
6. Frontend redirects to registration page

**Postconditions:**
- All user data is permanently deleted
- User is logged out
- User can create new account with same username/email

**Alternative Flows:**
- Invalid JWT → return 401 Unauthorized

---

## Use Case 5: Update User Goals
**Actor:** Frontend (triggered by User)
**Scenario:** The user updates their daily nutritional goals.
**Trigger:** User submits updated goals in a modal.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a PUT /api/user/me/goals request with new goal values.
2. Backend validates JWT and extracts user ID.
3. Backend validates input (all values must be >= 0).
4. Backend updates user's goals in the database.
5. Backend returns updated goals.

**Postconditions:**
- User's goals are updated.
- New goals are used for progress calculations.

**Alternative / Exception Flows:**
- Invalid JWT → return 401 Unauthorized.
- Invalid input (negative values) → return 400 Bad Request.

---