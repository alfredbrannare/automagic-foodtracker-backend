# System Requirements

## 1. User Authentication
**Epic:** User Authentication

### Functional Requirements
1. The system should allow a new user to register with a unique username, email, and password.
2. Passwords should be securely hashed before storage using BCrypt.
3. The system should generate JWT access and refresh tokens upon successful registration.
4. The system should provide default nutritional goals upon registration.
5. Username and email must be unique.
6. Users should be able to login with username and password.
7. The system should validate credentials and generate JWT tokens upon successful login.
8. Invalid credentials should return an appropriate error message with status 401.
9. The system should support token refresh for session management.
10. Users should be able to logout and clear their authentication tokens.
11. Users should be able to set and update their daily nutritional goals (protein, carbs, fat, calories).
12. Users should be able to delete their account and all associated data.

---

## 2. Storage Logging
**Epic:** Storage Logging

### Functional Requirements
1. Users should be able to add items to storage with name, total weight, nutrition values per 100g, weight per meal, and optional date.
2. Users should be able to set a low stock threshold for each storage item (in grams).
3. The system should initialize consumed weight to 0 when creating a new storage item.
4. The system should automatically calculate remaining weight (total weight - consumed weight).
5. The system should automatically calculate number of meals remaining ((totalWeight - consumedWeight) / weightPerMeal).
6. The system should automatically mark storage items as "low stock" when remaining weight is less than or equal to the threshold.
7. Low stock items should be visually distinguished in the user interface.
8. Users should be able to delete items from storage.
9. Users should be able to update storage items including name, total weight, nutrition values, weight per meal, and low stock threshold.
10. When updating storage, consumed weight should be preserved (not reset).
11. The system should recalculate remaining weight, meals remaining, and low stock status after any update.
12. Users should be able to view the storage log including all items with their current status.
13. Each storage item should display name, consumed weight of total weight, remaining meals, meals remaining, low stock status, and created at.
14. The system should automatically update consumed weight when meals are logged from storage.
15. The system should decrease consumed weight when meals from storage are deleted.

---

## 3. Meal Logging
**Epic:** Meal Logging

### Functional Requirements
1. Users should be able to log a meal from an existing storage item.
2. Users should be able to specify portion weight when logging from storage.
3. Nutrition should be calculated automatically for meals from storage.
4. The system should automatically update the storage item's consumed weight when a meal is logged from storage.
5. The system should check and update the storage item's low stock status after logging a meal.
6. Users should be able to log custom/on-the-go meals with manual nutrition input.
7. Custom meals should not be linked to any storage item.
8. Users should be able to specify the consumption time for any meal (default: current time).
9. Users should be able to update logged meals including name, weight, nutrition values, and consumption time.
10. When updating a meal from storage, the system should adjust the storage's consumed weight.
11. Users should be able to delete logged meals.
12. When deleting a meal connected to storage, the system should decrease the storage's consumed weight.
13. Users should be able to view their meal history for up to 2 weeks back.

---

## 4. Nutritional Tracking
**Epic:** Nutritional Tracking

### Functional Requirements
1. The system should calculate daily nutrition totals by summing all meals for a given date.
2. Daily totals should include protein, carbs, fat, and calories.
3. Users should be able to view daily nutrition summary for any date (default: today).
4. The system should compare daily nutrition totals against the user's goals.
5. The system should calculate progress percentage.
6. The system should indicate if the user is over, under, or on target for each goal.
7. Daily nutrition totals should update automatically when meals are added, updated, or deleted.

---

## 5. Non-Functional Requirements

### Security
1. All passwords must be hashed using BCrypt.
2. JWT tokens must be used for authentication and authorization.
3. All API endpoints must validate user ownership. Users cannot access or modify other users' data.
4. Tokens should be stored securely in HTTP-only cookies.

---

## Notes
- All API requests require JWT authentication except registration and login endpoints.
- Backend validation must enforce user ownership for all operations.
- Frontend and backend are developed in separate repositories but communicate via RESTful API.