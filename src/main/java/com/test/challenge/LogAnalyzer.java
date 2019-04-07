package com.test.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.challenge.cache.EventCache;
import com.test.challenge.model.LogEvent;
import com.test.challenge.processor.EventsProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class LogAnalyzer implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(LogAnalyzer.class);

    private EventCache eventCache;
    private EventsProcessor eventsProcessor;

    @Autowired
    public LogAnalyzer(EventCache eventCache, EventsProcessor eventsProcessor) {
        this.eventCache = eventCache;
        this.eventsProcessor = eventsProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(LogAnalyzer.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            System.err.println("Filename required");
            System.exit(-1);
        }
        String filename = args[0]; // input file path

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            log.info("Processing file {}", filename);
            String eventLine;
            while ((eventLine = reader.readLine()) != null) {
                if (!eventLine.trim().isEmpty()) {
                    eventsProcessor.processEvent(eventLine);
                }
            }
            if (!eventCache.isEmpty()) {
                log.warn("{} unpaired events found", eventCache.size());
                if (log.isTraceEnabled()) {
                    for (LogEvent unpairedEvent : eventCache.events()) {
                        log.trace("Unpaired event found: {}", unpairedEvent);
                    }
                }
            }
        }
        catch (IOException e) {
            log.error("Cannot process file {}", filename, e);
        }
    }
}
