package com.cafe.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Bill implements Serializable{
	
	private static final long serialVersionUID= 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String uuid;
	private String name;
	private String email;
	private String contactNumber;
	private String paymentMethod;
	private Integer total;
	private String productDetail;
	private String createdBy;
	

}

