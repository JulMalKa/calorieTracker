# Calorie Tracker

A REST API for tracking daily calorie and macronutrient intake. It allows you to manage users, a food product database, and meals composed of specific quantities of these products. Based on this data, it calculates caloric summaries (per meal and for the entire day).

## Tech Stack

- Java 25, Spring Boot
- Spring Data JPA + PostgreSQL
- Spring Security (password hashing via BCrypt)
- MapStruct (entity to DTO mapping) + Lombok
- Validation via Jakarta Bean Validation
- JUnit 5 + Mockito
- Docker (application and database containerization)

## Data Model

- **Users** — user data and their daily caloric/macronutrient goals
- **Product** — food product with nutritional values per 100g
- **Meal** — a meal assigned to a user, with a type (`BREAKFAST`, `LUNCH`, `DINNER`, `SNACKS`) and date
- **MealEntry** — a single entry in a meal: a specific product in a specific amount of grams

A user can have a maximum of one meal of a given type per day — attempting to add a duplicate results in a `409 Conflict` error.

## Endpoints

### Products — `/api/products`
| Method | Path | Description |
|---|---|---|
| GET | `/api/products` | list of all products |
| GET | `/api/products/{id}` | product details |
| POST | `/api/products` | add a product |
| PUT | `/api/products/{id}` | edit a product |
| DELETE | `/api/products/{id}` | delete a product |

### Users — `/api/users`
| Method | Path | Description |
|---|---|---|
| GET | `/api/users` | list of users |
| GET | `/api/users/{id}` | user details |
| POST | `/api/users` | register a user |
| PUT | `/api/users/{id}` | edit user data |
| DELETE | `/api/users/{id}` | delete a user |

### Meals — `/api/meals`
| Method | Path | Description |
|---|---|---|
| POST | `/api/meals` | create a meal |
| GET | `/api/meals/user/{userId}` | meals for a specific user |
| GET | `/api/meals/{mealId}/calories` | meal with calculated calories and macronutrients |
| GET | `/api/meals/user/{userId}/date/{date}/summary` | full daily summary (sum of calories/macros vs daily goal) |
| DELETE | `/api/meals/{id}` | delete a meal |

### Meal entries — `/api/meal/{mealId}/meal-entries`
| Method | Path | Description |
|---|---|---|
| POST | `/api/meal/{mealId}/meal-entries` | add a product to a meal |
| GET | `/api/meal/{mealId}/meal-entries` | list of entries in a meal |
| DELETE | `/api/meal/{mealId}/meal-entries/{id}` | delete an entry from a meal |

## Error Handling

The application features centralized exception handling (`GlobalExceptionHandler`), which maps errors to appropriate HTTP codes and returns a consistent JSON format:

- `404 Not Found` — resource (product, user, meal, meal entry) does not exist
- `409 Conflict` — attempt to create a duplicate (e.g., a second meal of the same type on the same day)
- `400 Bad Request` — request validation errors, with a list of fields and messages

## Running with Docker

Requirements: Java 25, Maven, PostgreSQL, Docker.

The project includes a `Dockerfile` (multi-stage build — building via Maven first, followed by a lightweight image with only the JRE) and a `docker-compose.yaml`, which sets up the application alongside a PostgreSQL database.

1. Run everything with a single command:
   ```
   docker compose --env-file .env up --build
   ```
2. The application will be accessible at `http://localhost:8080`, and the database at `localhost:5432`. Database data is stored in the `pgdata` volume, so it will survive container restarts.

## Tests

```
./mvnw test
```

The project includes unit tests for the service layer (business logic: macronutrient calculation, meal duplicate detection, resource existence validation).