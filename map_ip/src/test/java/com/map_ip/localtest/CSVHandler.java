package com.map_ip.localtest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thuan
 */
public class CSVHandler {
    public static List<List<String>> getCsvStringList(File file) {
        List<List<String>> csvStringList = new ArrayList<>();

        try {
            CSVParser parser = CSVParser.parse(file,
                    Charset.defaultCharset(), CSVFormat.DEFAULT);

            Iterator iterator;
            for (CSVRecord csvRecord : parser) {
                ArrayList<String> item = new ArrayList<>();
                iterator = csvRecord.iterator();
                while (iterator.hasNext()) {
                    String attr = iterator.next().toString();
                    item.add(attr);
                }
                csvStringList.add(item);
            }
        } catch (IOException e) {
            System.err.println("COULD NOT PARSE CSV: " + e.getMessage());
        }
        return csvStringList;
    }
}
