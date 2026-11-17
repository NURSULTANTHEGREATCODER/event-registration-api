# Event Registration API

## Description

This project is a Spring Boot REST API for managing events.

It was developed as part of Laboratory Work 7.

The application supports CRUD operations:
- Create events
- Read events
- Update events
- Delete events
- Search events by title

## Technologies

- Java 17
- Spring Boot
- Gradle
- REST API

## How to Run the Application

**Requirements:**
- JDK 17 or later
- IntelliJ IDEA or another Java IDE

**Steps:**

1. Clone the repository:

```bash
git clone https://github.com/YOUR-USERNAME/event-registration-api.git
```

2. Open the project folder.

3. Make sure JDK 17 or later is configured.

4. Run the application using Gradle:

On Windows:

```powershell
.\gradlew.bat bootRun
```

On Linux or macOS:

```bash
./gradlew bootRun
```

5. The application will be available at:

`http://localhost:8080`

## API Endpoints

| HTTP Method | Endpoint | Description |
|---|---|---|
| GET | `/api/events` | Get all events |
| GET | `/api/events/{id}` | Get an event by ID |
| GET | `/api/events?title=quiz` | Search events by title |
| POST | `/api/events` | Create a new event |
| PUT | `/api/events/{id}` | Update an existing event |
| DELETE | `/api/events/{id}` | Delete an event |
| GET | `/api/hello` | Get welcome message |
| GET | `/api/info` | Get application information |

## Example JSON Body

To create a new event, send a POST request to:

`http://localhost:8080/api/events`

Set the request header:

`Content-Type: application/json`

Example request body:

```json
{
    "title": "Spring Boot Workshop",
    "location": "Almaty"
}
```

The server automatically assigns a unique ID to the new event.

## HTTP Response Codes

| Status Code | Description |
|---|---|
| 200 OK | Request successful |
| 201 Created | Event successfully created |
| 204 No Content | Event successfully deleted |
| 400 Bad Request | Invalid or empty input |
| 404 Not Found | Event does not exist |

## Data Storage

Events are stored in an in-memory list.

Changes are not permanent and disappear when the application restarts.