package kz.iitu.registration.service;

import kz.iitu.registration.model.EventInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    public List<EventInfo> getAllEvents() {
        return List.of(
                new EventInfo(1L, "Web Design Workshop", "Almaty"),
                new EventInfo(2L, "JavaScript Quiz", "Astana"),
                new EventInfo(3L, "IT Career Talk", "Shymkent")
        );
    }

    public EventInfo getEventById(Long id) {
        return getAllEvents().stream()
                .filter(event -> event.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}