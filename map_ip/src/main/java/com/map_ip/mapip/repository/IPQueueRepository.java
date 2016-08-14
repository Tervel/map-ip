package com.map_ip.mapip.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import static com.map_ip.mapip.global.Constants.KEY_IP_NAME;
import static com.map_ip.mapip.global.Constants.TABLE_NAME_IPQUEUE;

/**
 * Author: Thuan Nguyen on 13/08/2016.
 */
public class IPQueueRepository {
    private Table queueTable = null;
    public IPQueueRepository (DynamoDB dynamoDB) {
        this(dynamoDB, TABLE_NAME_IPQUEUE);
    }

    public IPQueueRepository (DynamoDB dynamoDB, String tableName) {
        queueTable = dynamoDB.getTable(tableName);
    }

    public boolean addIP(String ip) {
        try {
            queueTable.putItem(
                    new Item()
                            .withPrimaryKey(KEY_IP_NAME, ip)
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeIP(String ip){
        return false;
    }
}
