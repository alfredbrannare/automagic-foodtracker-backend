# Use Cases — Meal Logging

---

## Use Case 1: Log Meal from Storage
**Actor:** Frontend (triggered by User)  
**Scenario:** The user selects a storage item and logs it as a meal.  
**Trigger:** User submits a meal form linked to a storage item.

**Preconditions:**
- The user is authenticated.
- The selected storage item exists.

**Main Flow:**
1. Frontend sends a `POST /meals` request with `storageId` and portion weight.
2. Backend validates JWT and user ownership.
3. Backend retrieves the storage item from the database.
4. Backend calculates nutrition for the given portion.
5. Backend creates a meal record.
6. Backend updates storage item’s remaining weight.
7. Backend returns success response with meal data.

**Postconditions:**
- Meal record is saved in the database.
- Storage item’s weight is updated.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Storage item not found → return `404 Not Found`.
- Invalid JWT → return `401 Unauthorized`.

**Related Use Cases:**
- Log On-the-Go Meal
- View Daily Nutrition

---

## Use Case 2: Log On-the-Go Meal
**Actor:** Frontend (triggered by User)  
**Scenario:** The user logs a meal that isn’t from storage.  
**Trigger:** User submits a custom meal form manually.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a `POST /meals` request without `storageId`.
2. Backend validates JWT and user ID.
3. Backend validates manual input (name, nutrition, weight).
4. Backend saves the meal record in the database.
5. Backend returns success response with meal data.

**Postconditions:**
- Meal record is stored in the database.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- Invalid input → return `400 Bad Request`.

**Related Use Cases:**
- Log Meal from Storage
- View Daily Nutrition

---

## Use Case 3: Delete Meal
**Actor:** Frontend (triggered by User)  
**Scenario:** The user deletes a logged meal.  
**Trigger:** User selects “delete” on a meal.

**Preconditions:**
- The user is authenticated.
- The meal exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `DELETE /meals/{id}` request.
2. Backend validates JWT and ownership.
3. Backend deletes the meal record.
4. Backend returns success confirmation.

**Postconditions:**
- Meal record is removed from the database.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Meal not found → return `404 Not Found`.
- User not owner → return `403 Forbidden`.

**Related Use Cases:**
- Log Meal from Storage
- View Daily Nutrition

---

## Use Case 4: View Daily Nutrition
**Actor:** Frontend (triggered by User)  
**Scenario:** The user views their daily nutrition summary.  
**Trigger:** User opens the dashboard or daily overview.

**Preconditions:**
- The user is authenticated.
- Meals have been logged.

**Main Flow:**
1. Frontend sends a `GET /meals/nutrition?date=...` request.
2. Backend validates JWT and extracts user ID.
3. Backend converts date to from and to Instant.
4. Backend aggregates nutrition data for the date range.
5. Backend returns daily totals for protein, carbs, fat, and kcal.

**Postconditions:**
- User sees current daily nutrition totals.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- No meals found → return zero totals.

**Related Use Cases:**
- Log Meal from Storage
- Log On-the-Go Meal
- Delete Meal
