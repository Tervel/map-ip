package com.map_ip.localtest;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.map_ip.mapip.MapIPApplication;
import com.map_ip.mapip.db.TableOperations;

import static com.map_ip.mapip.global.Constants.DEFAULT_DB_PORT;
import static com.map_ip.mapip.global.Constants.TABLE_NAME_EVENTS;

/**
 * Created by Thuan
 *
 * For running a local version of the application and database
 *
 */
public class LocalServer extends MapIPApplication {
    private static LocalDatabase dbserver = null;

    private static class ShutdownSequence extends Thread {
        @Override
        public void run() {
            //do something on shutdown
            dbserver.after();
            System.out.println("Exiting App");
        }
    }

    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().addShutdownHook(new ShutdownSequence());
        try {
            dbserver = new LocalDatabase(DEFAULT_DB_PORT);
            dbserver.before();
            EventsDummy.createAndPopulateEventsTable(dbserver.db());
            listTableItems(dbserver.db());
        } catch (Throwable e) {
            System.err.println("Failed to recreate Dynamo Server: " + e.getMessage());
            return;
        }

        new MapIPApplication().main(args);
    }

    public static void listTableItems(DynamoDB dynamoDB){
        System.out.println("Listing Generated Table Items: ");
        Table events = dynamoDB.getTable(TABLE_NAME_EVENTS);
        TableOperations.listAllTableItems(events);
    }
}
