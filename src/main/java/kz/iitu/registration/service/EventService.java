
package kz.iitu.registration.service;

import kz.iitu.registration.model.EventInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    // Mutable list that stores events in memory
    private final List<EventInfo> events = new ArrayList<>();

    // Server-side ID counter
    private long nextId = 4L;

    // Constructor: initialize sample events
    public EventService() {
        events.add(new EventInfo(1L, "Web Design Workshop", "Almaty"));
        events.add(new EventInfo(2L, "JavaScript Quiz", "Astana"));
        events.add(new EventInfo(3L, "IT Career Talk", "Shymkent"));
    }

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    // GET: return all events
    public List<EventInfo> getAllEvents() {
        return new ArrayList<>(events);
    }

    // GET: find event by ID
    public EventInfo getEventById(Long id) {
        return events.stream()
                .filter(event -> event.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    // GET: search events by title
    public List<EventInfo> searchEvents(String title) {
        return events.stream()
                .filter(event -> event.title()
                        .toLowerCase()
                        .contains(title.toLowerCase()))
                .toList();
    }

    // POST: create a new event
    public EventInfo createEvent(EventInfo event) {
        EventInfo newEvent = new EventInfo(
                nextId++,
                event.title(),
                event.location()
        );

        events.add(newEvent);

        return newEvent;
    }

    // PUT: update an existing event
    public EventInfo updateEvent(Long id, EventInfo updatedEvent) {

        for (int i = 0; i < events.size(); i++) {

            if (events.get(i).id().equals(id)) {

                EventInfo event = new EventInfo(
                        id,
                        updatedEvent.title(),
                        updatedEvent.location()
                );

                events.set(i, event);

                return event;
            }
        }

        return null;
    }

    // DELETE: remove event by ID
    public boolean deleteEvent(Long id) {
        return events.removeIf(event -> event.id().equals(id));
    }
}
