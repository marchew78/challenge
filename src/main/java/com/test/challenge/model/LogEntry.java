package com.test.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @Id
    private String id;
    long duration;
    String type;
    String host;
    boolean alert;

    public static LogEntry fromEvent(LogEvent event, long duration, boolean alert) {
        return new LogEntry(event.getId(),
                            duration,
                            event.getType(),
                            event.getHost(),
                            alert);
    }
}
