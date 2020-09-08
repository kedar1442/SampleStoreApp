package com.demoproject.rest.controller;

import java.net.URI;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demoproject.dataaccesslayer.entity.Product;
import com.demoproject.rest.controller.product.beans.ProductInfo;
import com.demoproject.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// Entity Beans are used and returned by this call to web layer. Ideally they should be different.

@RestController
@RequestMapping(value = "onlinestore",description="Manage Products")
public class ProductController {

	final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@PostMapping(value = "/products", produces = "application/json")
	public ResponseEntity<Product> createProduct(
			@ApiParam(value = "Data for the new product", required = true) @Valid @RequestBody ProductInfo productInfo) {
		logger.info("Input recieved to create product = " + productInfo);
		Product product = productService.createProduct(productInfo);
		logger.info("Created product with id = " + product.getId());

		logger.info("Setting header url with newly created product= " + product.getId());
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>(product, responseHeaders, HttpStatus.CREATED);
	}


	@DeleteMapping(value = "/products/{id}", produces = "application/json")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}

	
	@GetMapping(value = "/products", produces = "application/json")
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}


	@GetMapping(value = "/products/{id}", produces = "application/json")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
	}


	@PutMapping(value = "/products/{id}", produces = "application/json")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductInfo productInfo, @PathVariable Long id) {
		Product prod = productService.updateProduct(productInfo, id);
		logger.info("updated product id = " + prod.getId());
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}

}
