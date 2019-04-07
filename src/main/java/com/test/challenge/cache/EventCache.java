package com.test.challenge.cache;

import com.test.challenge.model.LogEvent;

import java.util.Collection;

public interface EventCache {
    /**
     * Adds an event to cache
     */
    void addEvent(LogEvent event);

    /**
     * Removes and returns (if found) an event with given id
     */
    LogEvent removeEvent(String eventId);

    /**
     * Returns if cache is empty
     */
    boolean isEmpty();

    /**
     * Returns cache size
     */
    int size();

    /**
     * Returns all events in cache
     */
    Collection<LogEvent> events();
}
