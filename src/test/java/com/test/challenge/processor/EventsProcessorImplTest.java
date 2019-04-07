package com.test.challenge.processor;

import com.test.challenge.cache.EventCache;
import com.test.challenge.model.LogEntry;
import com.test.challenge.repository.LogEntryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventsProcessorImplTest {

    private final static String EVENT_1 =
            "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", " + "\"host\":\"12345\", \"timestamp\":1491377495212}";
    private final static String EVENT_2 =
            "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", " + "\"host\":\"12345\", \"timestamp\":1491377495217}";

    @Autowired
    EventCache cache;

    @Test
    public void shouldSaveEntry() {
        // given
        LogEntryRepository repository = mock(LogEntryRepository.class);
        EventsProcessorImpl processor = new EventsProcessorImpl(cache, repository);

        // when
        processor.processEvent(EVENT_1);
        processor.processEvent(EVENT_2);

        // then
        verify(repository, times(1)).save(any(LogEntry.class));
    }

    //TODO tests if proper data is saved

    @Configuration
    @ComponentScan(basePackageClasses = EventCache.class)
    public static class SpringConfig {

    }
}
