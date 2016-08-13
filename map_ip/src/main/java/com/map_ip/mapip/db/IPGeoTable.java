package com.map_ip.mapip.db;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

import static com.map_ip.mapip.global.Constants.*;

/**
 * Author: Thuan Nguyen on 13/08/2016.
 */
public class IPGeoTable {
    public static final String KEY_IP_CITY = "city";

    public static Table createIPGeoTable(DynamoDB dynamoDB, String tableName) throws InterruptedException{
        Table ipGeoTable = null;
        ipGeoTable = dynamoDB.createTable(tableName,
                Arrays.asList(
                        new KeySchemaElement(KEY_IP_NAME, KeyType.HASH)
                ),
                Arrays.asList(
                        new AttributeDefinition(KEY_IP_NAME, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_IP_CITY, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_IP_COUNTRYCODE, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_IP_LAT, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_IP_LON, ScalarAttributeType.S),
                        new AttributeDefinition(KEY_IP_UPDATE_TIME, ScalarAttributeType.S)
                ),
                new ProvisionedThroughput(50L, 50L)
        );
        ipGeoTable.waitForActive();
        return ipGeoTable;
    }
}
