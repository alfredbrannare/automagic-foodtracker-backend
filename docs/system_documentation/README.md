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
- [User Authentication](use_cases/users_use-cases.md)
- [Storage Management](use_cases/storage_use-cases.md)
- [Meal Logging](use_cases/meal_use-cases.md)

### User Stories & Epics
- [User Authentication & Profile](user_stories/users/users_user_stories.md) - Epic: User Authentication
- [Storage Logging](user_stories/storage/storage_user_stories.md) - Epic: Storage Logging
- [Meal Logging](user_stories/meal/meal_user_stories.md) - Epic: Meal Logging

### UML Diagrams
- [Use Case Diagram - Users](diagrams/users_use-cases.png)
- [Use Case Diagram - Storage](diagrams/storage_use-cases.png)
- [Use Case Diagram - Meals](diagrams/usecase/meals_use-cases.png)
- [Class Diagram](diagrams/class/class_diagram_overview.png)

## System Focus

Three core functionalities:
1. **Storage Management** - Meal prep tracking with low stock warnings
2. **Meal Logging** - From storage or custom entries
3. **Nutritional Tracking** - Daily totals vs personal goals

**Stack:**
- Backend: Spring Boot (Java)
- Frontend: React (separate repo)
- Database: PostgreSQL
- Auth: JWT

---

*Examination Project - January 2025*