package com.timemanager.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.InetAddress;

@Configuration
@ComponentScan("com.timemanager")
public class MongoDBConfig {

    @Value("${mongodb.db.name}")
    public String dbName;

    @Value("${mongodb.password}")
    private String dbPassword;

    @Value("${mongodb.user}")
    private String dbUser;

    @Value("${mongodb.port}")
    private int dbPort;

    @Value("${mongodb.host}")
    private String dbHost;

    @Bean
    public MongoClient mongoClient() throws Exception {
        ServerAddress serverAddress = new ServerAddress(dbHost, dbPort);
        /*MongoCredential credential = MongoCredential.createScramSha1Credential(
                dbUser,
                dbName,
                dbPassword.toCharArray());*/

        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().connectTimeout(30000).build();

        MongoClient mongoClient = new MongoClient(serverAddress, mongoClientOptions);
        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(),dbName);
        return mongoTemplate;
    }

}