package com.cafe.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillService {
	
	@Autowired
	private CategoryService categoryService;
	
//	public ResponseEntity<String> generateReport(Map<String, Object> requestmap){
//		log.info("Inside generate Report");
//		try {
//			String fileName;
//			if(validateRequestMap(requestmap)) {
//				if(requestmap.containsKey("isGenerate") && !(Boolean) requestmap.get("isGenerate")) {
//					fileName= (String) requestmap.get("uuid");
//				}else {
//					fileName= categoryService.getUuid();
//					requestmap.put("uuid", fileName);
//					inserBill(requestmap);
//				}
//			}
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required data not found");
//		}catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//		}
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured");
//	}
	
	

	private Boolean validateRequestMap(Map<String, Object> requestMap){
		return requestMap.containsKey("name") &&
			   requestMap.containsKey("contactNumber") &&
			   requestMap.containsKey("email") &&
			   requestMap.containsKey("paymentMethod") &&
			   requestMap.containsKey("productDetail") &&
			   requestMap.containsKey("total");
				
	}
	
//	public static String getUUID() {
//		Date date= new Date();
//		long time= date.getTime();
//		return "BILL-"+ time;
//		
//	}
	
	private void inserBill(Map<String, Object> requestmap) {
		try {
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
