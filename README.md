# Food Ordering Backend

Spring Boot 3.5, Java 21, Spring Web, Spring Data JPA, Spring Security, Validation and MySQL.

## Run locally
1. Install JDK 21 and MySQL.
2. Create the database (optional; the URL can create it):
   `CREATE DATABASE food_ordering_db;`
3. Set MySQL credentials in `src/main/resources/application.properties`, or set `DB_USERNAME` and `DB_PASSWORD`.
4. From this folder run: `mvn spring-boot:run`
5. API base URL: `http://localhost:8080/api`

Demo admin: `admin@foodie.local` / `Admin@123`

## API overview
- `POST /api/auth/register` `{ "name":"Asha", "email":"asha@example.com", "password":"secret123" }`
- `POST /api/auth/login` `{ "email":"asha@example.com", "password":"secret123" }`
- `GET /api/auth/me`, `POST /api/auth/logout`
- `GET /api/foods`, `GET /api/foods/available`
- Admin session: `POST /api/foods`, `PUT /api/foods/{id}`, `DELETE /api/foods/{id}`
- `POST /api/orders` (login required), `GET /api/orders/my`
- Admin: `GET /api/orders`, `PATCH /api/orders/{id}/status?status=PREPARING`

Order example:
`{"customerName":"Asha","email":"asha@example.com","address":"Hyderabad","paymentMethod":"CASH_ON_DELIVERY","items":[{"foodId":1,"quantity":2}]}`

The backend uses session cookies. The frontend sends requests with credentials enabled. This is a local learning/demo project; before public deployment, add CSRF protection, stronger validation/rate limits, secure cookie settings, migrations, and production secrets.
