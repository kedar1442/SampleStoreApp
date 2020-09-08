package com.demoproject.rest.controller;

import java.net.URI;

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

import com.demoproject.dataaccesslayer.entity.Bill;
import com.demoproject.rest.controller.bill.beans.BillUpdateInfo;
import com.demoproject.service.BillService;
import com.demoproject.util.BillStatus;



@RestController
@RequestMapping(value = "onlinestore")
public class BillController {
	@Autowired
	private BillService billService;

	final Logger logger = LogManager.getLogger(getClass());

	@PostMapping(value = "/bills", produces = "application/json")
	public ResponseEntity<Bill> createBill() {
		logger.info("Request recieved to create Bill = ");
		Bill bill = billService.createBill(new Bill(0.0, 0, BillStatus.IN_PROGRESS));
		logger.info("Created Bill with id = " + bill.getId());
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		logger.info("Setting header url with newly created Bill= " + bill.getId());
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>(bill, responseHeaders, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/bills/{id}", produces = "application/json")
	public ResponseEntity<String> deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}

	@GetMapping(value = "/bills", produces = "application/json")
	public ResponseEntity<Iterable<Bill>> getAllBills() {
		return new ResponseEntity<>(billService.getAllBills(), HttpStatus.OK);
	}

	@GetMapping(value = "/bills/{id}", produces = "application/json")
	public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
		return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
	}


	@PutMapping(value = "/bills/{id}", produces = "application/json")
	public ResponseEntity<Bill> updateBill(@RequestBody BillUpdateInfo billUpdateInfo, @PathVariable Long id) {
		Bill updated = billService.updateBill(billUpdateInfo, id);
		logger.info("Request recieved =  " + billUpdateInfo);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

}
