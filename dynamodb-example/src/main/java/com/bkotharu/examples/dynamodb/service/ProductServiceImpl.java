package com.bkotharu.examples.dynamodb.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.bkotharu.examples.dynamodb.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private DynamoDB dynamoDB;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	// @Autowired
	// private DynamoDBMapper dynamoDBMapper;

	@Override
	public void insertProduct(Product product) {
		/*
		 * Table table = dynamoDB.getTable("PRODUCT"); String uuid1 =
		 * UUID.randomUUID().toString(); Item item = new Item().withPrimaryKey("id",
		 * uuid1).withString("Name", product.getName()).withNumber("Price",
		 * product.getPrice()); PutItemOutcome outcome = table.putItem(item);
		 */
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
		String uuid1 = UUID.randomUUID().toString();
		product.setId(uuid1);
		dynamoDBMapper.save(product);

	}

	@Override
	public ItemCollection<QueryOutcome> getProductByName(String productName) {
		Table table = dynamoDB.getTable("PRODUCT");

		QuerySpec spec = new QuerySpec().withKeyConditionExpression("Name = :name")
				.withValueMap(new ValueMap().withString(":name", productName)).withMaxPageSize(10);

		ItemCollection<QueryOutcome> items = table.query(spec);

		return items;

	}

	@Override
	public ItemCollection<QueryOutcome> getProductById(String productId) {
		Table table = dynamoDB.getTable("PRODUCT");

		QuerySpec spec = new QuerySpec().withKeyConditionExpression("id = :id")
				.withValueMap(new ValueMap().withString(":id", productId)).withMaxPageSize(10);

		ItemCollection<QueryOutcome> items = table.query(spec);
		return items;

	}

	@Override
	public void getProduct(Product product) {
		// TODO Auto-generated method stub

	}

}
