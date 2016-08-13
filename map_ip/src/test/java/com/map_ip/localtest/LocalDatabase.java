package com.map_ip.localtest;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.google.common.base.Joiner;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.map_ip.mapip.global.Constants.DEFAULT_DB_PORT;
import static org.junit.Assert.assertTrue;

/**
 * Author: Thuan Nguyen
 *
 */
public class LocalDatabase extends ExternalResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDatabase.class);

    private static final int DEFAULT_PORT = DEFAULT_DB_PORT;

    private static final File JAVA_HOME = new File(System.getProperty("java.home"));
    private static final String DYNAMO_DBLOCAL_JAR_NAME = "DynamoDBLocal.jar";
    private static final File DYNAMODB_LOCAL_HOME = new File(System.getProperty("basedir", "."), "dynamodb-dist");
    private static final File DYNAMODB_LOCAL_LIB = new File(DYNAMODB_LOCAL_HOME, "DynamoDBLocal_lib");
    private static final File DYNAMODB_LOCAL_JAR = new File(DYNAMODB_LOCAL_HOME, DYNAMO_DBLOCAL_JAR_NAME);
    private static final String PATH_SEPARATOR = System.getProperty("path.separator");

    private Process dynamoDBProcess;
    private DynamoDB dynamoDB;
    private final int port;

    public LocalDatabase() {
        this(DEFAULT_PORT);
    }

    public LocalDatabase(int port) {
        this.port = port;
    }

    public DynamoDB db() {
        return dynamoDB;
    }

    @Override
    public void before() throws Throwable {
        System.out.println(
                JAVA_HOME + "\n"
                + DYNAMODB_LOCAL_HOME + "\n"
                + DYNAMODB_LOCAL_JAR + "\n"
                + DYNAMODB_LOCAL_LIB + "\n");

        verifyDistPresent();
        dynamoDBProcess = startDynamoDBProcess();
        dynamoDB = new DynamoDB(initDynamoClient());
    }

    @Override
    public void after() {
        try{
            dynamoDB.shutdown();
            dynamoDBProcess.destroy();
        } catch (Exception e){
            LOGGER.error("Shutdown failed, you might need to kill stuff manually");
        }
    }

    public String getEndpoint() {
        return "http://localhost:" + port;
    }

    private void verifyDistPresent() {
        assertTrue("DynamoDB Local not found - please see readme about dynamodb-dist"
                + "\n" + DYNAMODB_LOCAL_HOME.getAbsolutePath(), DYNAMODB_LOCAL_JAR.exists());
    }

    private Process startDynamoDBProcess() throws IOException {
        return new ProcessBuilder()
                .command(new File(JAVA_HOME, "bin/java").getAbsolutePath(),
                        "-Djava.library.path=" + Joiner.on(PATH_SEPARATOR).join(DYNAMODB_LOCAL_HOME, DYNAMODB_LOCAL_LIB),
                        "-jar", DYNAMO_DBLOCAL_JAR_NAME,
                        "-inMemory",
                        "-sharedDb",
                        "-port", String.valueOf(port))
                .directory(DYNAMODB_LOCAL_HOME)
                .redirectErrorStream(true)
                .start();
    }

    private AmazonDynamoDBClient initDynamoClient() {
        final AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials("dummy", "dummy"));
        client.setEndpoint(getEndpoint());
        return client;
    }
}