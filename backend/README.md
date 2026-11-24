# orders-service

Small Spring Boot REST service (Java 17, Spring Boot 3.3, PostgreSQL) used as the practice project
for the Claude Code code-review training. **It contains deliberate flaws. Do not use it in production.**

## Run with Docker

Set `ADMIN_API_KEY` in your environment before starting the service.

```
docker compose up --build
```

The app listens on http://localhost:8080, PostgreSQL on localhost:5432. Sample data is seeded on first start.

## Run locally (database in Docker, app from Maven)

```
docker compose up -d db
mvn spring-boot:run
```

Set `ADMIN_API_KEY` in the environment used to run Maven.

## Endpoints

| Method | Path | Purpose |
|--------|------|---------|
| GET | /api/orders | List orders |
| GET | /api/orders/{id} | One order |
| POST | /api/orders | Create order |
| POST | /api/orders/{id}/cancel | Cancel order |
| GET | /api/orders/search?status=NEW | Search by status |
| GET | /api/customers | List customers |
| GET | /api/customers/{id} | One customer |
| POST | /api/customers | Create customer |
| DELETE | /api/admin/orders | Delete all orders (needs X-Api-Key) |
| GET | /actuator/health | Health |

Example:

```
curl -X POST http://localhost:8080/api/orders -H "Content-Type: application/json" \
  -d '{"customerId":1,"items":[{"productName":"Widget","quantity":2,"unitPrice":9.99}]}'
```

## Tests

```
mvn test
```

## Preparing the training repo

The review lessons compare a branch against main:

```
git init -b main
git add . && git commit -m "Initial orders-service"
git checkout -b feature/discount-changes
```

Then make a few edits on the branch (for example change the discount rules or add a new endpoint)
and commit them, so `git diff main...HEAD` has something to review.

The answer key for the planted flaws is in docs/FLAWS.md. Do not read it before the lessons.
