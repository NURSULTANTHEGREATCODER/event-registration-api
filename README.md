# Event Registration API --- Laboratory Work 8

A Spring Boot REST API for managing events. Laboratory Work 8 extends
the Lab 7 application by storing event records in PostgreSQL with Spring
Data JPA instead of keeping them in an in-memory list.

## 1. Technologies

-   Java 17
-   Spring Boot 4.1.2
-   Spring Web MVC
-   Spring Data JPA
-   PostgreSQL
-   Gradle

## 2. Project structure

``` text
src/
└── main/
    ├── java/kz/iitu/registration/
    │   ├── EventRegistrationApiApplication.java
    │   ├── controller/
    │   │   └── RegistrationController.java
    │   ├── model/
    │   │   └── Event.java
    │   ├── repository/
    │   │   └── EventRepository.java
    │   └── service/
    │       └── EventService.java
    └── resources/
        └── application.properties
```

## 3. Requirements

Install the following before running the application:

1.  JDK 17.
2.  PostgreSQL.
3.  IntelliJ IDEA or another Java IDE (optional).
4.  Postman or another HTTP client for API testing.

## 4. PostgreSQL configuration

### Step 1. Create the database

1.  Start the PostgreSQL server.
2.  Open pgAdmin 4.
3.  Create a database named `event_db`.

The database and table have different names:

-   Database: `event_db`
-   Table: `events`

You do not need to create the `events` table manually. Hibernate creates
or updates it when the application starts because the project uses:

``` properties
spring.jpa.hibernate.ddl-auto=update
```

### Step 2. Configure the connection

Open:

``` text
src/main/resources/application.properties
```

Use this configuration:

``` properties
spring.application.name=event-registration-api

app.course=Web Technologies
app.semester=3

spring.datasource.url=jdbc:postgresql://localhost:5432/event_db
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

`DB_USER` and `DB_PASSWORD` are environment variables containing the
PostgreSQL role name and its password. For a local installation, the
role is often named `postgres`. Use the password assigned to that
PostgreSQL role, not a pgAdmin application password.

### Step 3. Set environment variables

**Windows PowerShell**

Open PowerShell in the project directory and run these commands. Replace
the example password with your own PostgreSQL password:

``` powershell
$env:DB_USER="postgres"
$env:DB_PASSWORD="your_postgresql_password"
```

Then start the application in the same PowerShell window using the
command in Section 5.

**IntelliJ IDEA**

1.  Open **Run → Edit Configurations**.
2.  Select `EventRegistrationApiApplication`.
3.  Open **Modify options** and enable **Environment variables**.
4.  Enter the variables, using your actual PostgreSQL credentials:

``` text
DB_USER=postgres;DB_PASSWORD=your_postgresql_password
```

5.  Click **Apply**, then **OK**.

> **Security:** Never commit real database passwords to GitHub or
> include them in screenshots. The project archive used during
> development had a password written directly in
> `application.properties`. If that password was real, change it in
> PostgreSQL. Replace the hard-coded value with `${DB_PASSWORD}` as
> shown above before publishing the project. Also check that no
> credentials are present in Git history.

## 5. Build and run

### Run from IntelliJ IDEA

1.  Open the project as a Gradle project.
2.  Wait for Gradle to download the dependencies.
3.  Configure `DB_USER` and `DB_PASSWORD` as described in Section 4.
4.  Run `EventRegistrationApiApplication`.

### Run on Windows using Gradle Wrapper

Open PowerShell in the project root, set the environment variables, and
run:

``` powershell
$env:DB_USER="postgres"
$env:DB_PASSWORD="your_postgresql_password"
.\gradlew.bat bootRun
```

To run the tests:

``` powershell
.\gradlew.bat test
```

The API starts at:

``` text
http://localhost:8080
```

On successful startup, Hibernate connects to `event_db` and creates or
updates the `events` table.

## 6. Event data format

An event has three fields:

Field        Type       Description
  ------------ ---------- --------------------------------
`id`         `Long`     Database-generated primary key
`title`      `String`   Event title
`location`   `String`   Event location

Example JSON:

``` json
{
  "title": "Java Workshop",
  "location": "Almaty"
}
```

The database generates the `id`. Do not provide an ID when creating an
event. On update, the ID in the URL identifies the event to change.

## 7. API endpoints

Base URL:

``` text
http://localhost:8080
```

  ---------------------------------------------------------------------------------
Method            Endpoint                   Description       Success status
  ----------------- -------------------------- ----------------- ------------------
GET               `/api/events`              Get all events    `200 OK`

GET               `/api/events?title=java`   Search by partial `200 OK`
title, ignoring   
case

GET               `/api/events/{id}`         Get one event by  `200 OK`
ID

POST              `/api/events`              Create an event   `201 Created`

PUT               `/api/events/{id}`         Update an         `200 OK`
existing event

DELETE            `/api/events/{id}`         Delete an event   `204 No Content`

GET               `/api/hello`               Get the welcome   `200 OK`
message

GET               `/api/info`                Get application   `200 OK`
information
  ---------------------------------------------------------------------------------

## 8. Example requests

Use Postman or another HTTP client. For POST and PUT, select **Body →
raw → JSON** and send the `Content-Type: application/json` header.

### 8.1. Create an event

**POST** `http://localhost:8080/api/events`

Request body:

``` json
{
  "title": "Java Workshop",
  "location": "Almaty"
}
```

Expected status: `201 Created`

Example response:

``` json
{
  "id": 1,
  "title": "Java Workshop",
  "location": "Almaty"
}
```

The generated ID may be different.

### 8.2. Get all events

**GET** `http://localhost:8080/api/events`

Expected status: `200 OK`

Example response:

``` json
[
  {
    "id": 1,
    "title": "Java Workshop",
    "location": "Almaty"
  }
]
```

### 8.3. Search events by title

**GET** `http://localhost:8080/api/events?title=java`

Expected status: `200 OK`

The search matches part of the title and ignores uppercase/lowercase
differences. If no events match, the response is an empty array:

``` json
[]
```

### 8.4. Get an event by ID

**GET** `http://localhost:8080/api/events/1`

Expected status:

-   `200 OK` if the event exists.
-   `404 Not Found` if the ID does not exist.

Replace `1` with the ID returned by your POST request.

### 8.5. Update an event

**PUT** `http://localhost:8080/api/events/1`

Request body:

``` json
{
  "title": "Advanced Java Workshop",
  "location": "Astana"
}
```

Expected status:

-   `200 OK` if the event was updated.
-   `404 Not Found` if the ID does not exist.

The existing database ID is preserved.

### 8.6. Delete an event

**DELETE** `http://localhost:8080/api/events/1`

Expected status:

-   `204 No Content` if the event was deleted.
-   `404 Not Found` if the ID does not exist.

## 9. Validation and HTTP status codes

The controller checks that `title` and `location` are present and are
not blank when creating or updating an event.

Situation                         HTTP status
  --------------------------------- -------------------
Event created                     `201 Created`
GET or PUT successful             `200 OK`
Event deleted                     `204 No Content`
Missing or blank title/location   `400 Bad Request`
Event ID does not exist           `404 Not Found`

Example invalid request body:

``` json
{
  "title": " ",
  "location": "Almaty"
}
```

Expected status: `400 Bad Request`.

## 10. Verify records in PostgreSQL

1.  Open pgAdmin 4.
2.  Select the `event_db` database.
3.  Open **Tools → Query Tool**.
4.  Execute:

``` sql
SELECT id, title, location
FROM events;
```

The returned rows should correspond to the events returned by the API.

## 11. Persistence test

To confirm that data is stored in PostgreSQL and not only in application
memory:

1.  Create an event using `POST /api/events`.
2.  Save the generated event ID.
3.  Stop the Spring Boot application.
4.  Start the application again.
5.  Send `GET /api/events/{id}` using the saved ID.
6.  Confirm that the event is still returned.
7.  Optionally, run the SQL query from Section 10 in pgAdmin 4.

The event should remain in the database after the application restarts.

## 12. Author

-   Student:Amanbek Nursultan
-   Group:IT3 2501 DS
-   **Course:** Web Technologies
-   **Laboratory work:** 8 --- Spring Data JPA and PostgreSQL
