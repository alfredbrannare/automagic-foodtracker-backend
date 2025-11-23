## Use Case: Update User Goals
**Actor:** Frontend (triggered by User)
**Scenario:** The user updates their daily nutritional goals.
**Trigger:** User submits updated goals in a modal.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a `PUT /user/me/goals` request with new goal values.
2. Backend validates JWT and extracts user ID.
3. Backend validates input (all values must be positive).
4. Backend updates user's goals in the database.
5. Backend returns updated goals.

**Postconditions:**
- User's goals are updated.
- New goals are used for progress calculations.

**Alternative / Exception Flows:**
- Invalid JWT → return 401 Unauthorized.
- Invalid input → return 400 Bad Request.

---