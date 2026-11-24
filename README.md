## What it is: 
Spring Boot 3.3, Java 17, Maven, PostgreSQL 16. It has orders, order items and customers, and seeds sample data on first start. Run it with docker compose up --build, which starts the database and the app on http://localhost:8080. The README lists the endpoints and curl examples.

## Planted flaws: 
There are 20, all listed in docs/FLAWS.md, so hide that file before the review lessons.
### - The four from the training: 
An N+1 query when listing orders, no input validation, a hard-coded API key, and untested discount branches (VIP and bulk).
### - Others: 
SQL injection in the status search, customer entities returned directly (leaking internal notes), client-supplied prices, no @Transactional, double for money, a swallowed exception in cancel, PII in logs, field injection and a root container.
