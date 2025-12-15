# Use Cases – Nutritional Tracking

---

## Use Case 1: View Daily Nutrition Summary
**Actor:** User (via Frontend)  
**Scenario:** User views their total nutritional intake for a specific date  
**Trigger:** User selects date on frontend

**Preconditions:**
- User is authenticated

**Main Flow:**
1. Frontend sends GET /api/meals/summary?date=YYYY-MM-DD
2. Backend validates JWT token
3. Backend converts date to Instant range (start of day to end of day UTC)
4. Backend retrieves all meals for the date range
5. Backend sums nutrition values (protein, carbs, fat, kcal)
6. Backend rounds values to 1 decimal place
7. Backend returns daily nutrition totals
8. Frontend displays totals to user

**Postconditions:**
- User sees total protein, carbs, fat, and calories consumed
- Values are accurate and up-to-date

**Alternative Flows:**
- No meals logged for date → return zeros for all values
- No date specified → default to today's date
- Invalid JWT → return 401 Unauthorized

---

## Use Case 2: Compare Progress vs Goals
**Actor:** User (via Frontend)  
**Scenario:** User views how their daily nutrition compares to personal goals  
**Trigger:** Viewing nutrition dashboard (automatic)

**Preconditions:**
- User is authenticated
- User has nutritional goals set

**Main Flow:**
1. System displays daily nutrition totals
2. System retrieves user's goals from profile
3. System calculates progress for each macro:
    - Protein: (consumed / goal) × 100
    - Carbs: (consumed / goal) × 100
    - Fat: (consumed / goal) × 100
    - Calories: (consumed / goal) × 100
4. System determines status for each macro:
    - On target: 90-110% of goal
    - Under: < 90% of goal
    - Over: > 110% of goal
5. Frontend displays comparison with visual indicators (colors, progress bars)

**Postconditions:**
- User sees comparison for each macro
- User sees if they're meeting, exceeding, or below targets
- Visual feedback helps user understand progress

**Alternative Flows:**
- No goals set → use default goals (150g protein, 250g carbs, 60g fat, 2000 kcal)
- No meals logged → show 0% progress for all macros

---

## Use Case 3: View Nutrition History
**Actor:** User (via Frontend)  
**Scenario:** User views nutrition for past dates  
**Trigger:** User selects a past date with arrows

**Preconditions:**
- User is authenticated
- Historical meal data exists

**Main Flow:**
1. User selects a date in the past (up to 2 weeks back)
2. Frontend sends GET /api/meals/summary?date=YYYY-MM-DD
3. Backend retrieves meals for selected date
4. Backend calculates nutrition totals
5. Frontend displays historical nutrition data
6. System also shows goals comparison for that date

**Postconditions:**
- User can see their eating patterns over time
- Historical data remains accurate

**Alternative Flows:**
- Date more than 2 weeks old → may not have data
- No meals for selected date → show zeros

---