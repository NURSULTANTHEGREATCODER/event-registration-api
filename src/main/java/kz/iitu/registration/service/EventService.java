
package kz.iitu.registration.service;

import kz.iitu.registration.model.Event;
import kz.iitu.registration.repository.EventRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String getWelcomeMessage() {
        return "Welcome to the Event Registration API";
    }

    // GET: return all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET: find event by ID
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    // GET: search events by title
    public List<Event> searchEvents(String title) {
        return eventRepository.findByTitleContainingIgnoreCase(title);
    }

    // POST: create a new event
    public Event createEvent(Event event) {

        // Ignore client-provided ID
        event.setId(null);

        return eventRepository.save(event);
    }

    // PUT: update an existing event
    public Event updateEvent(Long id, Event updatedEvent) {

        Event existingEvent = eventRepository.findById(id)
                .orElse(null);

        if (existingEvent == null) {
            return null;
        }

        existingEvent.setTitle(updatedEvent.getTitle());
        existingEvent.setLocation(updatedEvent.getLocation());

        return eventRepository.save(existingEvent);
    }

    // DELETE: remove event by ID
    public boolean deleteEvent(Long id) {

        if (!eventRepository.existsById(id)) {
            return false;
        }

        eventRepository.deleteById(id);

        return true;
    }
}
