# Event Registration API

## Laboratory Work 6
**Introduction to Spring Boot and Dependency Injection**

### Project Description
This project is a simple REST API developed using Spring Boot. It demonstrates the use of controllers, services, dependency injection, and application configuration.

### Technologies
- Java 17
- Spring Boot
- Spring Web
- Gradle
- IntelliJ IDEA

### API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/hello` | Returns a welcome message |
| GET | `/api/events` | Returns the list of events |
| GET | `/api/info` | Returns application information |
| GET | `/api/events/{id}` | Returns an event by ID |

### How to Run

1. Open the project in IntelliJ IDEA.
2. Run `EventRegistrationApplication.java`.
3. Wait until the application starts.
4. Open the following address in your browser:

`http://localhost:8080/api/events`

### Dependency Injection

The `RegistrationController` uses constructor-based dependency injection to receive the `EventService` object. This allows the controller to use the service without creating it manually.

### Configuration

Application settings are stored in `application.properties`, including the application name, course, and semester.

### Conclusion

This project demonstrates the basic principles of Spring Boot application development, REST API creation, and dependency injection.