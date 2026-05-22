# University Management System

Coursework for the Software Development course, Vilnius University, 2026.

A Java EE 8 web application demonstrating core enterprise patterns: CDI, JPA, JSF, EJB, and JAX-RS. Deployed on WildFly 26 with an embedded H2 database.

### RESTful API

Base URL: `http://localhost:8080/uni-management/api`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/students` | List all students |
| `GET` | `/students/{id}` | Get student by id |
| `POST` | `/students` | Create a new student |
| `PUT` | `/students/{id}` | Update an existing student |

Example:

```bash
# List all students
curl http://localhost:8080/uni-management/api/students

# Create
curl -X POST -H "Content-Type: application/json" \
  -d '{"firstName":"Jonas","lastName":"Jonaitis","email":"jonas@stud.vu.lt"}' \
  http://localhost:8080/uni-management/api/students

# Update
curl -X PUT -H "Content-Type: application/json" \
  -d '{"firstName":"Jonas","lastName":"Petraitis","email":"jonas@stud.vu.lt"}' \
  http://localhost:8080/uni-management/api/students/1
```

## Running locally

1. Start WildFly 26 and deploy the `university-ds` H2 datasource.
2. Build and deploy:
   ```bash
   mvn clean package
   # deploy uni-management.war to WildFly deployments/
   ```
3. Open `http://localhost:8080/uni-management/`