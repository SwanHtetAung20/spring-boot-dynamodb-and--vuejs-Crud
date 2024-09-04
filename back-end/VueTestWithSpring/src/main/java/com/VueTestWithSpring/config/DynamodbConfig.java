package com.VueTestWithSpring.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamodbConfig {

    @Bean
    public DynamoDBMapper dynamoDBMapper(){
        return new DynamoDBMapper(amazonDbConfig());
    }

    private AmazonDynamoDB amazonDbConfig() {
        return null;
    }
}
