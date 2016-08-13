package com.map_ip.map_ip.repository;

import com.map_ip.mapip.model.DynamoEvent;
import com.map_ip.mapip.repository.EventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.map_ip.localtest.LocalTestConstants.*;


@RunWith(MockitoJUnitRunner.class)
public class EventRepositoryTest extends LocalDynamoRepositoryTest {

    @Test
    public void shouldFindNewlyCreatedEvent() {
        EventRepository repository = new EventRepository(getDynamoDB());
        DynamoEvent event = new DynamoEvent(EVENT_CASE_IP, EVENT_CASE_TYPE, EVENT_CASE_DATETIME);
        Assert.assertTrue(repository.addEvent(event));
        Assert.assertTrue(repository.contains(event));
    }

}