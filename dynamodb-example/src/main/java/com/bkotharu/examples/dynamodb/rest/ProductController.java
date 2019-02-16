package com.bkotharu.examples.dynamodb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.FailureHandler.Outcome;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.bkotharu.examples.dynamodb.entity.Product;
import com.bkotharu.examples.dynamodb.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/name/{name}")
	public ItemCollection<QueryOutcome> getProductByName(@PathVariable("name") String productName) {
		return productService.getProductByName(productName);
	}

	@GetMapping("/id/{id}")
	public ItemCollection<QueryOutcome> getProductById(@PathVariable("id") String id) {
		return productService.getProductById(id);
	}

	@PostMapping
	public ResponseEntity<?> insertProduct(@RequestBody Product product) {

		productService.insertProduct(product);
		return ResponseEntity.ok().build();

	}
}
