# Epic: Storage Logging

## User Story 1: Log an item to storage
**As a** user  
**I want to** log an item to storage 
**So that** I can track what I have in storage

**Acceptance Criteria:**
- User can enter item details manually
    - Name
    - Weight
    - Nutrition values
    - Date (Optional)
- Nutrition is calculated automatically

## User Story 2: Delete a storage item
**As a** user  
**I want to** delete a storage item
**So that** I can remove it from the log

**Acceptance Criteria:**
- User can delete a storage item from the log

## User Story 3: View storage log
**As a** user  
**I want to** view the storage log
**So that** I can see what and how much I have in storage

**Acceptance Criteria:**
- User can view the storage log
- User can see the total weight of the storage items
- User can see the remaining weight of the storage items
- User can see when items were added to storage

---

## User Story 4: Update storage item
**As a** user
**I want to** update a storage item
**So that** I can correct misinputs or add more meal prep

**Acceptance Criteria:**
- User can edit name, total weight, nutrition values, weight per meal, and threshold
- Consumed weight is preserved
- System recalculates remaining weight, meals remaining, and low stock status
- Changes are saved immediately

---

## User Story 5: Receive low stock warning
**As a** user
**I want to** be warned when a storage item is running low
**So that** I know when it's time to meal prep again

**Acceptance Criteria:**
- Storage items with remaining weight ≤ threshold are marked as "low stock"
- Low stock items are visually distinct (color)
- Warning appears in storage list view
- Warning updates automatically as meals are logged