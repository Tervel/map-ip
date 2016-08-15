package com.map_ip.mapip.db;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class TableOperations {
    private String Name;
    private String Port;

    public TableOperations(String name, String port) {
        this.Name = name;
        this.Port = port;
    }

    public String getName() {
        return this.Name;
    }

    public String getPort() {
        return this.Port;
    }

    public static void listTables(DynamoDB db) {

        TableCollection<ListTablesResult> tables = db.listTables();
        Iterator<Table> iterator = tables.iterator();

        System.out.println("\n");
        System.out.println("Listing tables ...");

        int tableCount = 0;
        while (iterator.hasNext()) {
            Table table = iterator.next();
            System.out.println("\nTable name : " + table.getTableName());
            tableCount++;
        }
        String tableCountMsgSuffix = tableCount == 1 ? "table exists" : "tables exist";
        System.out.println("\n");
        System.out.println(tableCount + " " + tableCountMsgSuffix);
        System.out.println("\n");
    }

    public static void getTableInformation(DynamoDB db, String tableName) {
        System.out.println("Describing " + tableName);

        TableDescription tableDescription = db.getTable(tableName).describe();
        System.out.format("Name: %s:\n" + "ApiStatus: %s \n"
                        + "Provisioned Throughput (read capacity units/sec): %d \n"
                        + "Provisioned Throughput (write capacity units/sec): %d \n",
                tableDescription.getTableName(),
                tableDescription.getTableStatus(),
                tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
                tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
    }

    public static JSONArray listAllIP(Table table){
//        Table table = dynamoDB.getTable(TABLE_NAME_IPGEO);
        JSONArray jsonArray = new JSONArray();
        try{
            ItemCollection<ScanOutcome> items = table.scan();
            Iterator<Item> iterator = items.iterator();
            JSONArray itemList = new JSONArray();

            System.out.println("\nLISTING ALL TABLE ITEMS FOR: " + table.getTableName() + "\n");
            while (iterator.hasNext()){
                Item item = iterator.next();
                itemList.put(new JSONObject(item.toJSON()));
            }

//            for(String string : itemList){
//                jsonArray.put(new JSONObject(string));
//            }
            System.out.println("\n");
        } catch (Exception e){
            System.err.println("\nUnable to scan the table: " + table.getTableName());
            System.err.println(e.getMessage() + "\n");
        }
        return jsonArray;
    }

    public static void listAllTableItems(Table table){
        try{
            ItemCollection<ScanOutcome> items = table.scan();
            Iterator<Item> iterator = items.iterator();
            ArrayList<String> itemList = new ArrayList<>();

            while (iterator.hasNext()){
                Item item = iterator.next();
                itemList.add(item.toString());
            }

            System.out.println("\nLISTING ALL TABLE ITEMS FOR: " + table.getTableName() + "\n");
            for(String string : itemList){
                System.out.println(string);
            }
            System.out.println("\n");
        } catch (Exception e){
            System.err.println("\nUnable to scan the table: " + table.getTableName());
            System.err.println(e.getMessage() + "\n");
        }
    }

    public static void deleteAllTables (DynamoDB db) throws InterruptedException{
        TableCollection<ListTablesResult> tables = db.listTables();
        Iterator<Table> iterator = tables.iterator();
        Table table;
        String tableName = null;

        System.out.println("\n");
        while(iterator.hasNext()){
            table = iterator.next();
            System.out.println("\n\nDELETING TABLE: " + table.getTableName() + "\n");
            table.delete();
            table.waitForDelete();
            System.out.println("\nTABLE: " + table.getTableName() + " DELETED\n");
        }
        System.out.println("\n");
    }
}
