# Automagic Food Tracker — Backend

Spring Boot REST API for the Automagic Food Tracker application.

## Tech Stack

- Java 25
- Spring Boot 4.x
- Spring Security (JWT Authentication)
- PostgreSQL
- Docker

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/alfredbrannare/automagic-foodtracker-backend.git
cd automagic-foodtracker-backend
```

### 2. Start the application

```bash
docker compose up --build
```

This starts both the Spring Boot app and PostgreSQL database.

The API will be available at `http://localhost:8080`

### 4. Test it

Register a user:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"password123"}'
```

## Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `JWT_SECRET` | Secret key for JWT signing (min 256 bits) | ✅ Yes |
| `DEMO_ENABLED` | Enable demo account (`true`/`false`) | No |

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register new user |
| `POST` | `/api/auth/login` | Login |
| `POST` | `/api/auth/logout` | Logout |
| `POST` | `/api/auth/refresh` | Refresh token |
| `GET` | `/api/auth/check` | Check if authenticated |
| `DELETE` | `/api/auth` | Delete account |

### User
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/user/me/goals` | Get nutrition goals |
| `PUT` | `/api/user/me/goals` | Update nutrition goals |

### Meals
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/meals?date=YYYY-MM-DD` | Get meals by date |
| `POST` | `/api/meals` | Create meal |
| `PUT` | `/api/meals/{id}` | Update meal |
| `DELETE` | `/api/meals/{id}` | Delete meal |
| `GET` | `/api/meals/summary?date=YYYY-MM-DD` | Get daily nutrition summary |

### Storage
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/storage` | Get all storage items |
| `POST` | `/api/storage` | Create storage item |
| `PUT` | `/api/storage/{id}` | Update storage item |
| `DELETE` | `/api/storage/{id}` | Delete storage item |

## Development

To run only the database and start the app manually:

```bash
docker compose --profile dev up -d db-dev
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## License

MIT