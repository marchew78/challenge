package com.test.challenge.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.challenge.cache.EventCache;
import com.test.challenge.model.LogEntry;
import com.test.challenge.model.LogEvent;
import com.test.challenge.repository.LogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EventsProcessorImpl implements EventsProcessor {
    private static Logger log = LoggerFactory.getLogger(EventsProcessorImpl.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private EventCache eventCache;
    private LogEntryRepository logEntryRepository;

    @Value("${alert.threshold.ms}")
    long alertThresholdMs;

    @Autowired
    public EventsProcessorImpl(EventCache eventCache, LogEntryRepository logEntryRepository) {
        this.eventCache = eventCache;
        this.logEntryRepository = logEntryRepository;
    }

    @Override
    public void processEvent(String event) {
        LogEvent logEvent = parseEvent(event);

        if (logEvent != null) {
            log.trace("Found event {}", logEvent);

            LogEvent pairedEntry = eventCache.removeEvent(logEvent.getId());
            if (pairedEntry == null) {
                eventCache.addEvent(logEvent);
                return; // got first event of pair, continue reading
            }
            storeEntry(logEvent, pairedEntry);
        }
    }

    private void storeEntry(LogEvent logEvent, LogEvent pairedEntry) {
        long duration = Math.abs(pairedEntry.getTimestamp() - logEvent.getTimestamp());
        log.debug("Event with id {} took {}ms", logEvent.getId(), duration);
        LogEntry logEntry = LogEntry.fromEvent(logEvent, duration, duration > alertThresholdMs);
        logEntryRepository.save(logEntry);
    }

    private LogEvent parseEvent(String event) {
        try {
            return objectMapper.readValue(event, LogEvent.class);
        }
        catch (IOException e) {
            log.warn("Cannot parse event: {}", event, e);
            return null;
        }
    }
}
