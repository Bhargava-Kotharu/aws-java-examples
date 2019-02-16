package com.bkotharu.examples.dynamodb.service;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.bkotharu.examples.dynamodb.entity.Product;

public interface ProductService {

	public void insertProduct(Product product);

	public ItemCollection<QueryOutcome> getProductByName(String productName);

	public ItemCollection<QueryOutcome> getProductById(String productId);

	public void getProduct(Product product);
}
