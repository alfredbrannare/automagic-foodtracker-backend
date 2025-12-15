# Epic: Nutritional Tracking

## User Story 1: View daily nutrition totals
**As a** user  
**I want to** see my total nutrition for the day  
**So that** I know what I've consumed

**Acceptance Criteria:**
- User can see totals for protein, carbs, fat, and calories
- Totals update automatically when meals are added/edited/deleted
- User can view totals for any date (up to 2 weeks ago)
- Default view shows today's totals
- Values are rounded to 1 decimal place

---

## User Story 2: See progress towards goals
**As a** user  
**I want to** see how my daily nutrition compares to my goals  
**So that** I know if I'm on track

**Acceptance Criteria:**
- User sees comparison for each macro (protein, carbs, fat, calories)
- Progress is shown as absolute numbers (e.g., "120g / 150g")
- Progress is shown as percentage (e.g., "80%")
- Visual indicators show goal achievement status
- Color coding indicates: on target, under, or over

---

## User Story 3: Set and update nutritional goals
**As a** user  
**I want to** customize my daily nutritional targets  
**So that** they match my health and fitness goals

**Acceptance Criteria:**
- User can set goals for protein, carbs, fat, and calories
- User can modify goals at any time
- Changes are saved immediately
- System validates that all values are non-negative
- Default goals are provided for new users (150g protein, 250g carbs, 60g fat, 2000 kcal)

---

## User Story 4: Track nutrition over time
**As a** user  
**I want to** view my nutrition for past dates  
**So that** I can see my eating patterns

**Acceptance Criteria:**
- User can select any date to view nutrition (up to 2 weeks ago)
- System stores historical data accurately (up to 2 weeks ago)
- User can navigate between different dates (up to 2 weeks ago)
- Past data remains accessible (up to 2 weeks ago)