package com.map_ip.map_ip.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.map_ip.localtest.LocalDatabase;
import com.map_ip.mapip.db.EventsTable;
import org.junit.After;
import org.junit.Before;

import static com.map_ip.mapip.global.Constants.DEFAULT_DB_PORT;
import static com.map_ip.mapip.global.Constants.TABLE_NAME_EVENTS;

public class LocalDynamoRepositoryTest {
    private static LocalDatabase dbserver = null;
    private static DynamoDB dynamoDB = null;

    public static DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    @Before
    public void startupDatabaseServer() throws Throwable {
        dbserver = new LocalDatabase(DEFAULT_DB_PORT);
        dbserver.before();
        dynamoDB = dbserver.db();
        EventsTable.createEventsTable(getDynamoDB(), TABLE_NAME_EVENTS);
    }

    @After
    public void closeDatabaseServer() {
        dbserver.after();
    }

//    @Test
//    public void canCreateAndInsertToDBTable() throws InterruptedException {
//        System.out.println("\nListing tables BEFORE test run...");
//        EventsTable.createEventsTable(dynamoDB, TABLE_NAME_EVENTS);
//        System.out.println("\nListing tables AFTER test run...");
//        TableOperations.listTables(dynamoDB); //Should be one
//    }
}
