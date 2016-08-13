package com.map_ip.mapip;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.map_ip.mapip.db.EventsTable;
import com.map_ip.mapip.global.Constants;
import com.map_ip.mapip.health.TypeHealthCheck;
import com.map_ip.mapip.repository.EventRepository;
import com.map_ip.mapip.resources.EventResource;
import com.map_ip.mapip.service.EventService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Thuan Nguyen on 13/08/2016.
 */
public class MapIPApplication extends Application<MapIPConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapIPApplication.class);

    public static void main(String[] args) throws Exception {
        new MapIPApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MapIPConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(MapIPConfiguration configuration,
                    Environment environment) {
        String tablePort = Integer.toString(Constants.DEFAULT_DB_PORT);
        String dbAuthority = Constants.DB_AUTH_PATH;
        final AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint(dbAuthority + tablePort);
        final DynamoDB dbserver = new DynamoDB(client);
        final EventRepository eventRepository = new EventRepository(new DynamoDB(client));
        final EventService eventService = new EventService(eventRepository);
        final EventResource resource = new EventResource(eventService);
        environment.jersey().register(resource);

        // Health check
        final TypeHealthCheck healthCheck = new TypeHealthCheck(configuration.getRequestIp());
        environment.healthChecks().register("requestIP", healthCheck);

        // Start DB
        String tableName = "requests";
        try {
            EventsTable.createEventsTable(dbserver, tableName);
        } catch (Exception e) {
            LOGGER.info("Unable to create table: ");
            LOGGER.info(e.getMessage());
        }
    }

}
