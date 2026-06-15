---
name: java-springboot
description: 'Get best practices and architectural compliance rules for developing and reviewing applications with Spring Boot.'
---

# Spring Boot Best Practices & Agent Rules

Your goal is to help me write high-quality Spring Boot applications by following established clean code practices, enforcing our architectural boundaries, and auditing code using a standardized review template.

## 1. Clean Code

- **Dependency Injection:** Always use constructor-based injection for required dependencies. Declare fields as `private final` to enforce immutability.
- **Component Stereotypes:** Use `@Component`, `@Service`, `@Repository`, and `@Controller`/`@RestController` appropriately to define beans.
- **Naming Conventions:** Use CamelCase for classes, camelCase for methods/variables, UPPER_CASE for constants, and lowercase for package names.
- **Documentation:** Every public interface, public service method, and controller endpoint must have proper JavaDoc explaining its purpose, parameters, and return types.
- **Logging (SLF4J):** Declare loggers as `private static final Logger logger = LoggerFactory.getLogger(MyClass.class);`. Use parameterized messages (`logger.info("Processing user {}...", userId);`) instead of string concatenation to preserve memory.
- **Error Handling:** Centralize exceptions globally using a `@ControllerAdvice` combined with `@ExceptionHandler` methods to guarantee a consistent response payload structure.

## 2. Definition and Application for Arquitecture and Dependencies

The project follows a **High-Level Layer** organization and, internally, it is subdivided by **Domains/Modules**.

### Base Package Structure:
`com.unerp.[layer].[domain]`

### Example for layer structure (Domain: `product`):
  - `com.unerp.domain.product`: JPA Entities and product aggregates.
  - `com.unerp.repository.product`: Spring Data Repository interface for products.
  - `com.unerp.service.product`: Business logic and services for product.
  - `com.unerp.controller.product`: REST Controllers, Endpoints, and Request/DTO payload classes for products.

### Dependency and Coupling Rules (Allowed Flow):
1. **Call flow:** `controller` -> `service` -> `repository` -> `domain`. 
2. **Cross-Import Prohibition:** No class belonging to a domain should directly import components from another domain across different layers where it does not correspond (e.g., `com.unerp.controller.bills` cannot use `com.unerp.repository.product`). All inter-domain communication must be carried out strictly through the authorized service layer.
3. **Data Input Handling (Payloads):** POST/PUT methods must use specific request classes (`ProductRequest`, `CreateBillCommand`) in the `@RequestBody`. The controller or a mapper will be responsible for instantiating the domain entity from this payload before invoking the service. Validation annotations (`@Valid`, `@NotNull`, `@NotBlank`) are located within these request classes.

## 3. Severity Levels for code revision

For proyect code analysis, suggestions audits, the findings have to be categorized on these three strict levels:

- **MUST FIX (Blocking):**
  - Architectural violations such as cross-layer/cross-domain forbidden imports.
  - Business logic or persistence queries directly written inside controllers.
  - Absence of secure encoding in URL parameters (`encodeURIComponent` or equivalent sanitization).
  - Omission of required contexts or hardcoded configuration strings/secrets in the code.
  - Lack of basic exception handling or breakdown in the execution chain of security middlewares/filters.
- **SHOULD FIX (Non-blocking):**
  - Inline type declarations (inline types or raw data) instead of using structured classes or domain models.
  - Lack of sanitization/security validations (`.secure()`) on strings that have already been superficially validated.
  - Response structure or formatting inconsistent with the global REST design of the project.
  - Missing logger entries in alternative or error flows.
  - Indiscriminate use of ambiguous generic types (such as `Object` or untyped responses) beyond generic exception handling.
- **SUGGESTION:**
  - Optional improvements for code readability.
  - Minor refactoring opportunities.
  - Consistency in local variable naming.
  - Unit test coverage gaps for non-priority critical routes.

## 4. Output Format: Report Review

When requested to perform a code review or audit, analyze the code according to the principles above and return **only** the structured report below — no preamble, no narration.

```markdown
## Backend Review Report
- **Scope:** [PR #X / local changes / full audit]
- **Files reviewed:** [count and list grouped by layer]

- **Architecture compliance:**
| # | File:Line | Violation | Severity | Details |
|---|-----------|-----------|----------|---------|
| 1 | ...       | ...       | MUST FIX | ...     |

- **Code quality findings:**
| # | File:Line | Finding | Severity | Details |
|---|-----------|---------|----------|---------|
| 1 | ...       | ...     | SHOULD FIX | ...   |

- **Security findings:**
| # | File:Line | Issue | Severity | Details |
|---|-----------|-------|----------|---------|
| 1 | ...       | ...   | ...      | ...     |