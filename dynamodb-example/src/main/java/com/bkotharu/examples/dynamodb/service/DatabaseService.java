package com.bkotharu.examples.dynamodb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

@Service
public class DatabaseService {

	@Autowired
	private AmazonDynamoDB dynamoDB;

	public List<String> listAllTables() {
		ListTablesResult tablesList = dynamoDB.listTables();
		return tablesList.getTableNames();

	}

	public CreateTableResult createTable(String tableName) {
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("ID").withAttributeType("S"));

		ArrayList<KeySchemaElement> keySchema = new ArrayList<>();
		keySchema.add(new KeySchemaElement().withAttributeName("ID").withKeyType(KeyType.HASH));

		CreateTableRequest createTableReq = new CreateTableRequest().withTableName(tableName)
				.withAttributeDefinitions(attributeDefinitions).withKeySchema(keySchema).withProvisionedThroughput(
						new ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(5L));
		CreateTableResult result = dynamoDB.createTable(createTableReq);
		return result;
	}
}
