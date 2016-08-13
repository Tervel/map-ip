package com.map_ip.map_ip.repository;

import com.map_ip.map_ip.TestDataBuilder;
import com.map_ip.mapip.model.DynamoEvent;
import com.map_ip.mapip.model.api.ApiEvent;
import com.map_ip.mapip.repository.DynamoEventMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DynamoEventMapperTest {



    @Test
    public void shouldMapIPAndTypeIntoDynamoEvent() {
        ApiEvent event = TestDataBuilder.defaultApiEvent();
        DynamoEventMapper mapper = new DynamoEventMapper();
        DynamoEvent result = mapper.fromApiEventToDynamoEvent(event);
        assertEquals(event.getIp(), result.getIp());
        assertEquals(event.getType(), result.getType());
    }

}