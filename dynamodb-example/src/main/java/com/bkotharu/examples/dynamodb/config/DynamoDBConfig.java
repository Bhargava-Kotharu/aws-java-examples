package com.bkotharu.examples.dynamodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String endpoint;

	@Value("${amazon.dynamodb.region}")
	private String region;

	@Value("${amazon.dynamodb.accessKey}")
	private String accessKey;

	@Value("${amazon.dynamodb.secretKey}")
	private String secretKey;

	@Bean
	public AmazonDynamoDB getAmazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
				.withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
					@Override
					public String getAWSAccessKeyId() {
						return accessKey;
					}

					@Override
					public String getAWSSecretKey() {
						return secretKey;
					}
				})).build();
	}

	@Bean
	public DynamoDB getDynamoDB() {
		DynamoDB dynamoDB = new DynamoDB(getAmazonDynamoDB());
		return dynamoDB;
	}

	/*@Bean
	public DynamoDBMapper getDynamoDBMapper() {
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(getAmazonDynamoDB());
		return dynamoDBMapper;
	}*/
}
