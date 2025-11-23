# Use Cases — Storage Management

---

## Use Case 1: Log Storage Item
**Actor:** Frontend (triggered by User)  
**Scenario:** The user logs an item into their food storage.  
**Trigger:** User submits a storage item form.

**Preconditions:**
- The user is authenticated (valid JWT).
- The storage item exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `POST /api/storage` request with item data.
2. Backend validates JWT and extracts user ID.
3. Backend validates input (name, weight, nutrition values, date (optional)).
4. Backend saves the storage item in the database.
5. Backend returns success response with item details.

**Postconditions:**
- Storage item is saved in the database.
- Item is associated with the user.

**Alternative / Exception Flows:**
- Missing or invalid JWT → return `401 Unauthorized`.
- Invalid input → return `400 Bad Request`.

---

## Use Case 2: Delete Storage Item
**Actor:** Frontend (triggered by User)  
**Scenario:** The user deletes a storage item they previously added.  
**Trigger:** User selects “delete” on a storage item.

**Preconditions:**
- The user is authenticated.
- The item exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `DELETE /api/storage/{id}` request.
2. Backend validates JWT and ownership of the item.
3. Backend deletes the item from the database.
4. Backend returns 204.

**Postconditions:**
- Storage item is removed from the database.

**Alternative / Exception Flows:**
- Item not found → return `404 Not Found`.
- User does not own item → return `403 Forbidden`.

---

## Use Case 3: View Storage Log
**Actor:** Frontend (triggered by User)  
**Scenario:** The user views their current storage items and totals.  
**Trigger:** User navigates to the storage overview screen.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a `GET /api/storage` request.
2. Backend validates JWT and extracts user ID.
3. Backend retrieves all storage items for the user.
4. Backend calculates total and remaining weight.
5. Backend returns the storage list with totals.

**Postconditions:**
- Storage items and totals are displayed in the frontend.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- No items found → return empty list.

---

## Use Case 4: Update Storage Item
**Actor:** Frontend (triggered by User)
**Scenario:** The user updates an existing storage item.
**Trigger:** User edits a storage item and submits changes.

**Preconditions:**
- The user is authenticated.
- The storage item exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `PUT /api/storage/{id}` request with updated data.
2. Backend validates JWT and ownership.
3. Backend retrieves existing storage item.
4. Backend updates all fields (except ID) with new values.
5. Backend preserves consumed weight.
6. Backend recalculates remaining weight and low stock status.
7. Backend saves updated storage item.
8. Backend returns updated storage item.

**Postconditions:**
- Storage item is updated in the database.
- Low stock status is recalculated.

**Alternative / Exception Flows:**
- Storage item not found → return 404 Not Found.
- User does not own item → return 403 Forbidden.
- Invalid input → return 400 Bad Request.