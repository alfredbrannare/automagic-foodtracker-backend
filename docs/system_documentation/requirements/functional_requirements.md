# System Requirements

## 1. User Authentication
**Epic:** User Authentication

### Functional Requirements
1. The system should allow a new user to register with a unique username, email, and password.
2. Passwords should be securely hashed before storage.
3. The system should generate a JWT token upon successful registration.
4. Users should be able to login with username/email and password.
5. The system should validate credentials and generate a JWT token upon successful login.
6. Invalid credentials should return an appropriate error message.

---

## 2. Storage Logging
**Epic:** Storage Logging

### Functional Requirements
1. Users should be able to add items to storage with name, weight, nutrition values, and optional date.
2. Nutrition values should be automatically calculated.
3. Users should be able to delete items from storage.
4. Users should be able to view the storage log including total and remaining weights, and date added.

---

## 3. Meal Logging
**Epic:** Meal Logging

### Functional Requirements
1. Users should be able to log a meal from storage.
2. Nutrition should be calculated automatically for the meal portion.
3. Storage weight should be updated when a meal is logged from storage.
4. Users should be able to log custom/on-the-go meals with manual nutrition input.
5. Users should be able to delete logged meals.
6. Users should be able to view daily nutrition totals, calculated automatically.

---

## Notes
- All API requests require JWT authentication except registration and login.
- Backend validation must enforce ownership: users cannot modify other users' storage items or meals.
