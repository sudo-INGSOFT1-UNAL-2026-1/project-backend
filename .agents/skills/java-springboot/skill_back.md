---
name: java-springboot
description: 'Get best practices for developing applications with Spring Boot.'
---

# Spring Boot Best Practices

Your goal is to help me write high-quality Spring Boot applications by following established best practices.

## Project Setup & Structure

- **Build Tool:** Use Maven (`pom.xml`) for dependency management.
- **Starters:** Use Spring Boot starters (e.g., `spring-boot-starter-web`, `spring-boot-starter-data-jpa`) to simplify dependency management.
- **Package Structure:** Organize code by layer and feature/domain (e.g., `com.example.app.controller.order`, `com.example.app.user`).

## Dependency Injection & Components

- **Constructor Injection:** Always use constructor-based injection for required dependencies. This makes components easier to test and dependencies explicit.
- **Immutability:** Declare dependency fields as `private final`.
- **Component Stereotypes:** Use `@Component`, `@Service`, `@Repository`, and `@Controller`/`@RestController` annotations appropriately to define beans.

## Configuration

- **Externalized Configuration:** Use `application.yml` (or `application.properties`) for configuration. YAML is often preferred for its readability and hierarchical structure.

## Web Layer (Controllers)

- **RESTful APIs:** Design clear and consistent RESTful endpoints.
- **Request Payloads (DTOs):** Handlers for POST/PUT requests must use specific request classes (e.g., `ProductRequest`, `CreateBillCommand`) inside `@RequestBody`. Do not use Domain Entities directly as input parameters in controllers.
- **Entity Creation:** The controller (or a mapper) is responsible for taking the incoming request parameters and instantiating/building the corresponding Domain Entity before passing it to the service layer.
- **Validation:** Apply Java Bean Validation annotations (`@Valid`, `@NotNull`, `@NotBlank`, etc.) on these request payload classes to validate incoming data.
- **Error Handling:** Implement a global exception handler using `@ControllerAdvice` and `@ExceptionHandler` to provide consistent error responses.

## Service Layer

- **Business Logic:** Encapsulate all business logic within `@Service` classes.
- **Statelessness:** Services should be stateless.
- **Transaction Management:** Use `@Transactional` on service methods to manage database transactions declaratively. Apply it at the most granular level necessary.

## Data Layer (Repositories)

- **Spring Data JPA:** Use Spring Data JPA repositories by extending `JpaRepository` for standard database operations.
- **Custom Queries:** For complex queries, use `@Query` or the JPA Criteria API.
- **Projections:** Use DTO projections to fetch only the necessary data from the database.

## Logging

- **SLF4J:** Use the SLF4J API for logging.
- **Logger Declaration:** `private static final Logger logger = LoggerFactory.getLogger(MyClass.class);`
- **Parameterized Logging:** Use parameterized messages (`logger.info("Processing user {}...", userId);`) instead of string concatenation to improve performance.

## Testing

- **Unit Tests:** Write unit tests for services and components using JUnit 5 and a mocking framework like Mockito.
- **Integration Tests:** Use `@SpringBootTest` for integration tests that load the Spring application context.
- **Test slices:** Use test slice annotations like `@WebMvcTest` (for controllers) or `@DataJpaTest` (for repositories) to test specific parts of the application in isolation.
- **Testcontainers:** Consider using Testcontainers for reliable integration tests with real databases, message brokers, etc.

## Security

- **Spring Security:** The project uses basic Spring Security for authentication/authorization. There are no roles, profiles, nor `@PreAuthorize` annotations on methods; security is handled globally.
- **Password Encoding:** Always encode passwords using a strong hashing algorithm like BCrypt.
- **Input Sanitization:** Prevent SQL injection by using Spring Data JPA or parameterized queries. Prevent Cross-Site Scripting (XSS) by properly encoding output.

## Architecture Rules

- **Project Structure:** The project follows a **High-Level Layer** organization and, internally, it is subdivided by **Domains/Modules**.
- **Base Package Structure:** `com.unerp.[layer].[domain]`. Example for the 'product' domain:
  - `com.unerp.domain.product`: JPA Entities and product aggregates.
  - `com.unerp.repository.product`: Spring Data Repository interface for products.
  - `com.unerp.service.product`: Business logic and services for product.
  - `com.unerp.controller.product`: REST Controllers, Endpoints, and Request/DTO payload classes for products.
- **Strict rules to create functions:** To implement a new feature/function (e.g., 'bills'), replicate this exact package structure (`com.unerp.service.bills`, `com.unerp.controller.bills`, etc.). Do not mix domains in the same package.