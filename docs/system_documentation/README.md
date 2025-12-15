# OOAD Documentation

Object-Oriented Analysis and Design documentation for Automagic Meal Tracker.

## OOAD Process

1. **Requirements** → Functional requirements identified
2. **Use Cases** → User interactions documented
3. **Epics & User Stories** → User-centric features defined
4. **UML Diagrams** → System design visualized

## Contents

### Requirements
- [Functional Requirements](requirements/functional_requirements.md)

### Use Cases
- [User Authentication & Profile](use_cases/users_use-cases.md)
- [Storage Management](use_cases/storage_use-cases.md)
- [Meal Logging](use_cases/meal_use-cases.md)
- [Nutritional Tracking](use_cases/nutrition_use-cases.md)

### User Stories & Epics
- [User Authentication & Profile](user_stories/users/users_user_stories.md) - Epic: User Authentication
- [Storage Logging](user_stories/storage/storage_user_stories.md) - Epic: Storage Logging
- [Meal Logging](user_stories/meal/meal_user_stories.md) - Epic: Meal Logging
- [Nutritional Tracking](user_stories/nutrition/nutrition_user_stories.md) - Epic: Nutritional Tracking

### UML Diagrams

**Use Case Diagrams:**
- [Users Authentication](diagrams/usecase/users_authentication.puml)
- [Storage Management](diagrams/usecase/storage_management.puml)
- [Meals Management](diagrams/usecase/meals_management.puml)
- [Nutritional Tracking](diagrams/usecase/nutrition_tracking.puml)

**Class Diagram:**
- [Complete Class Diagram](diagrams/class/class_diagram_overview.puml)

**Sequence Diagram:**
- [Add Meal Sequence](diagrams/sequence/add_meal_sequence.puml)

**Activity Diagrams:**
- [Log Meal from Storage](diagrams/activity/meal/meals_log_activity.puml)
- [Delete Meal](diagrams/activity/meal/meal_delete_activity.puml)
- [Log Storage Item](diagrams/activity/storage/storage_log_activity.puml)
- [Delete Storage Item](diagrams/activity/storage/storage_delete_activity.puml)
- [User Registration/Login](diagrams/activity/user/users_log_activity.puml)

## System Focus

**Four core functionalities:**
1. **User Authentication** - Secure JWT-based registration, login, and profile management
2. **Storage Management** - Meal prep tracking with low stock warnings
3. **Meal Logging** - From storage or custom on-the-go entries
4. **Nutritional Tracking** - Daily totals compared against personal goals

**Stack:**
- Backend: Spring Boot (Java)
- Frontend: React (separate repo)
- Database: PostgreSQL
- Auth: JWT

---

*Examination Project - January 2025*