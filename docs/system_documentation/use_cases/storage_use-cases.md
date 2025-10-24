# Use Cases — Storage Management

---

## Use Case 1: Log Storage Item
**Actor:** Frontend (triggered by User)  
**Scenario:** The user logs an item into their food storage.  
**Trigger:** User submits a storage item form.

**Preconditions:**
- The user is authenticated (valid JWT).

**Main Flow:**
1. Frontend sends a `POST /storage` request with item data.
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

**Related Use Cases:**
- View Storage Log
- Delete Storage Item

---

## Use Case 2: Delete Storage Item
**Actor:** Frontend (triggered by User)  
**Scenario:** The user deletes a storage item they previously added.  
**Trigger:** User selects “delete” on a storage item.

**Preconditions:**
- The user is authenticated.
- The item exists and belongs to the user.

**Main Flow:**
1. Frontend sends a `DELETE /storage/{id}` request.
2. Backend validates JWT and ownership of the item.
3. Backend deletes the item from the database.
4. Backend returns 204.

**Postconditions:**
- Storage item is removed from the database.

**Alternative / Exception Flows:**
- Item not found → return `404 Not Found`.
- User does not own item → return `403 Forbidden`.

**Related Use Cases:**
- Log Storage Item
- View Storage Log

---

## Use Case 3: View Storage Log
**Actor:** Frontend (triggered by User)  
**Scenario:** The user views their current storage items and totals.  
**Trigger:** User navigates to the storage overview screen.

**Preconditions:**
- The user is authenticated.

**Main Flow:**
1. Frontend sends a `GET /storage` request.
2. Backend validates JWT and extracts user ID.
3. Backend retrieves all storage items for the user.
4. Backend calculates total and remaining weight.
5. Backend returns the storage list with totals.

**Postconditions:**
- Storage items and totals are displayed in the frontend.

**Alternative / Exception Flows:**
- Invalid JWT → return `401 Unauthorized`.
- No items found → return empty list.

**Related Use Cases:**
- Log Storage Item
- Delete Storage Item
