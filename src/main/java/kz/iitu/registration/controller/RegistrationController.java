
package kz.iitu.registration.controller;

import kz.iitu.registration.model.Event;
import kz.iitu.registration.service.EventService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final EventService eventService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.course}")
    private String course;

    @Value("${app.semester}")
    private String semester;

    public RegistrationController(EventService eventService) {
        this.eventService = eventService;
    }

    // GET /api/hello
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message",
                eventService.getWelcomeMessage()
        );
    }

    // GET /api/events
    @GetMapping("/events")
    public List<Event> getEvents(
            @RequestParam(required = false) String title
    ) {
        if (title == null || title.isBlank()) {
            return eventService.getAllEvents();
        }

        return eventService.searchEvents(title);
    }

    // GET /api/info
    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
                "applicationName", applicationName,
                "course", course,
                "semester", semester
        );
    }

    // GET /api/events/{id}
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(
            @PathVariable Long id
    ) {
        Event event = eventService.getEventById(id);

        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }

    // POST /api/events
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(
            @RequestBody Event event
    ) {
        if (event.getTitle() == null || event.getTitle().isBlank()
                || event.getLocation() == null
                || event.getLocation().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Title and location cannot be empty");
        }

        Event createdEvent = eventService.createEvent(event);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdEvent);
    }

    // PUT /api/events/{id}
    @PutMapping("/events/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @RequestBody Event event
    ) {
        if (event.getTitle() == null || event.getTitle().isBlank()
                || event.getLocation() == null
                || event.getLocation().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Title and location cannot be empty");
        }

        Event updatedEvent = eventService.updateEvent(id, event);

        if (updatedEvent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE /api/events/{id}
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id
    ) {
        boolean deleted = eventService.deleteEvent(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
