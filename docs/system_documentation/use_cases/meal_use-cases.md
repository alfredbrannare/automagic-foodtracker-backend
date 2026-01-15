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
1. Frontend sends a `POST /api/meals` request with `storageId` and portion weight.
2. Backend validates JWT and user ownership.
3. Backend retrieves the storage item from the database.
4. Backend calculates nutrition for the given portion.
5. Backend creates a meal.
6. Backend updates storage item’s consumed weight.
7. Backend returns success response with meal data.

**Postconditions:**
- Meal record is saved in the database.
- Storage item’s consumed weight is updated.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Storage item not found → return `404 Not Found`.
- Invalid JWT → return `401 Unauthorized`.

---

## Use Case 2: Log On-the-Go Meal
**Actor:** Frontend (triggered by User)  
**Scenario:** The user logs a meal that isn’t from storage.  
**Trigger:** User submits a custom meal form manually.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a `POST /api/meals` request without `storageId`.
2. Backend validates JWT and user ID.
3. Backend validates manual input (name, nutrition, weight).
4. Backend saves the meal in the database.
5. Backend returns success response with meal data.

**Postconditions:**
- Meal is stored in the database.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- Invalid input → return `400 Bad Request`.

---

## Use Case 3: Delete Meal
**Actor:** Frontend (triggered by User)  
**Scenario:** The user deletes a logged meal.  
**Trigger:** User selects “delete” on a meal.

**Preconditions:**
- The user is authenticated.
- The meal exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `DELETE /api/meals/{id}` request.
2. Backend validates JWT and ownership.
3. Backend deletes the meal. If meal is from storage: Backend updates storage's consumed weight.
4. Backend returns success confirmation.

**Postconditions:**
- Meal is removed from the database.
- Daily nutrition totals are updated.

**Alternative / Exception Flows:**
- Meal not found → return `404 Not Found`.
- User not owner → return `403 Forbidden`.

---

## Use Case 4: View Daily Nutrition
**Actor:** Frontend (triggered by User)  
**Scenario:** The user views their daily nutrition summary.  
**Trigger:** User opens the dashboard or daily overview.

**Preconditions:**
- The user is authenticated.
- Meals have been logged.

**Main Flow:**
1. Frontend sends a `GET /api/meals/summary?date=...` request.
2. Backend validates JWT and extracts user ID.
3. Backend converts date to from and to Instant.
4. Backend aggregates nutrition data for the date range.
5. Backend returns daily totals for protein, carbs, fat, and kcal.

**Postconditions:**
- User sees current daily nutrition totals.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- No meals found → return zero totals.

---

## Use Case: Update Meal
**Actor:** Frontend (triggered by User)
**Scenario:** The user updates a logged meal.
**Trigger:** User edits a meal entry and submits changes.

**Preconditions:**
- The user is authenticated.
- The meal exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `PUT /api/meals/{id}` request with updated data.
2. Backend validates JWT and ownership.
3. Backend retrieves existing meal.
4. If meal is from storage:
   a. Backend calculates weight difference.
   b. Backend updates storage's consumed weight.
   c. Backend recalculates storage's low stock status.
5. Backend updates meal fields.
6. Backend saves updated meal.
7. Backend returns updated meal.

**Postconditions:**
- Meal is updated in the database.
- If from storage: storage consumed weight is adjusted.

**Alternative / Exception Flows:**
- Meal not found → return 404 Not Found.
- User does not own meal → return 403 Forbidden.