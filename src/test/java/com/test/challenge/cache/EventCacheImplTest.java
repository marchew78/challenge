package com.test.challenge.cache;

import com.test.challenge.model.LogEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class EventCacheImplTest {
    private static final String EVENT_ID = "someId";
    private EventCache testee;


    @Before
    public void init() {
        testee = new EventCacheHashMapImpl();
    }

    @Test
    public void shouldBeEmpty() {
        // given
        LogEvent event = new LogEvent();
        event.setId(EVENT_ID);

        // when
        testee.addEvent(event);
        testee.removeEvent(EVENT_ID);

        // then
        assertTrue(testee.isEmpty());
    }

    @Test
    public void shouldNotBeEmpty() {
        // when
        testee.addEvent(new LogEvent());

        // then
        assertFalse(testee.isEmpty());
    }

    @Test
    public void shoulReturnSize() {
        // given
        LogEvent event = new LogEvent();
        event.setId(EVENT_ID);

        // when
        testee.addEvent(new LogEvent());
        testee.addEvent(event);

        // then
        assertEquals(2, testee.size());

        // when
        testee.removeEvent(EVENT_ID);

        // then
        assertEquals(1, testee.size());
    }

    @Test
    public void shoulAddAndRemoveEvent() {
        // given
        LogEvent event = new LogEvent();
        event.setId(EVENT_ID);

        // when
        testee.addEvent(event);
        testee.addEvent(new LogEvent());
        LogEvent removed = testee.removeEvent(EVENT_ID);

        // then
        assertEquals(event, removed);
    }

    @Test
    public void shoulReturnEvents() {
        // given
        for (int i = 0; i < 5; i++)
        {
            LogEvent event = new LogEvent();
            event.setId(String.valueOf(i));
            testee.addEvent(event);
        }

        // when
        Collection<LogEvent> events = testee.events();

        // then
        assertEquals(5, events.size());
    }
}
