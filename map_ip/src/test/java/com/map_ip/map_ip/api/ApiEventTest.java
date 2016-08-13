package com.map_ip.map_ip.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.map_ip.mapip.model.api.ApiEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.map_ip.localtest.LocalTestConstants.*;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ApiEventTest {

    @Test
    public void shouldDeserialise() throws Exception {
        final ApiEvent event = new ApiEvent(EVENT_CASE_IP, EVENT_CASE_TYPE, EVENT_CASE_DATETIME);
        final ObjectMapper mapper = new ObjectMapper();
        final ApiEvent result = mapper.readValue(fixture("fixtures/ApiEvent.json"), ApiEvent.class);
        assertEquals(event, result);
    }
}