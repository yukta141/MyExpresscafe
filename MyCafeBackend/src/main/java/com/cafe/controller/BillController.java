package com.cafe.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.service.BillService;

@RestController
@RequestMapping(path="/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	
//	@PostMapping(path="/generateReport")
//	public ResponseEntity<String> generateReport(@RequestBody Map<String, Object> requestmap){
//		 return 
//	}

}
