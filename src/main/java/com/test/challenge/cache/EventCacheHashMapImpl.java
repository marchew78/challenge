package com.test.challenge.cache;

import com.test.challenge.model.LogEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
public class EventCacheHashMapImpl implements EventCache {

    private HashMap<String, LogEvent> cache = new HashMap<>();

    @Override
    public void addEvent(LogEvent event) {
        cache.put(event.getId(), event);
    }

    @Override
    public LogEvent removeEvent(String eventId) {
        return cache.remove(eventId);
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public int size() {
        return cache.size();
    }

    public Collection<LogEvent> events() {
        return cache.values();
    }
}
