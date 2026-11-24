# Planted flaws (answer key)

Delete or hide this file before running review lessons, so Claude does not simply read the answers.

| # | Area | Location | Flaw |
|---|------|----------|------|
| 1 | Performance | OrderController.toSummary, Order.items / Order.customer (LAZY) | N+1 queries: listing orders loads customer and items one order at a time |
| 2 | Validation | CreateOrderRequest, OrderController.create | No @Valid or constraints: null customerId, negative quantity, empty items, negative price all accepted |
| 3 | Secrets | AppConstants.ADMIN_API_KEY | Hard-coded API key in source |
| 4 | Secrets | application.properties, docker-compose.yml | Hard-coded database password |
| 5 | Security | OrderService.searchByStatus | SQL injection through string-concatenated native query |
| 6 | Security | AdminController | Key compared with equals (not constant time); key is the hard-coded one; no real authentication elsewhere |
| 7 | Security | CustomerController.get / list | JPA entity returned directly, exposing internalNotes, phone and email |
| 8 | Security | CustomerController.create | Mass assignment: caller can set vip=true on the entity |
| 9 | Security | application.properties | All actuator endpoints exposed |
| 10 | Correctness | OrderService.createOrder | Unit price comes from the client (price tampering) |
| 11 | Correctness | OrderService.createOrder | No @Transactional: order saved twice, partial state possible on failure |
| 12 | Correctness | Order, OrderItem, OrderService | Money stored and calculated as double |
| 13 | Correctness | OrderService.cancel | Exception swallowed silently; cancelling SHIPPED orders is allowed; no state-transition rules |
| 14 | Correctness | OrderService.findById, createOrder, CustomerController.get | Optional.get() without check: missing id gives a 500 instead of 404 |
| 15 | Privacy | OrderService.createOrder | Customer name, email and phone written to logs |
| 16 | Design | OrderService, controllers | Field injection with @Autowired instead of constructor injection |
| 17 | API | OrderController.create | Returns 200 instead of 201; no pagination on list endpoints |
| 18 | Tests | DiscountService | VIP and bulk (>10 items) branches have no tests; OrderService.createOrder, cancel and search are untested |
| 19 | Ops | Dockerfile | Container runs as root; no HEALTHCHECK; no resource limits |
| 20 | Config | application.properties | ddl-auto=update and show-sql=true outside a dev profile |
