package kz.iitu.registration.controller;

import kz.iitu.registration.model.EventInfo;
import kz.iitu.registration.service.EventService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Constructor-based Dependency Injection
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
    public List<EventInfo> getEvents() {
        return eventService.getAllEvents();
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

    // BONUS: GET /api/events/{id}
    @GetMapping("/events/{id}")
    public ResponseEntity<EventInfo> getEventById(
            @PathVariable Long id
    ) {
        EventInfo event = eventService.getEventById(id);

        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(event);
    }
}