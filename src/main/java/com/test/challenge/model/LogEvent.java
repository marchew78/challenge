package com.test.challenge.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LogEvent {
    private String id;
    private State state;
    private long timestamp;
    private String type;
    private String host;

    public enum State {
        STARTED, FINISHED
    }
}
