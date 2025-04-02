package com.cafe.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class Product implements Serializable{
	
	public static final long serialVersionUID= 123456L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private Integer price;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

}
