package com.bkotharu.examples.dynamodb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.bkotharu.examples.dynamodb.service.DatabaseService;
import com.bkotharu.examples.dynamodb.service.ProductService;

@RestController
@RequestMapping("/tables")
public class DatabaseController {
 

	@Autowired
	private DatabaseService databaseService;

	@GetMapping
	public java.util.List<String> listTables() {
		return databaseService.listAllTables();
	}

	@PostMapping("/{tableName}")
	public CreateTableResult createTable(@PathVariable("tableName") String tableName) {
		CreateTableResult result = databaseService.createTable(tableName);
		return result;
	}
}
