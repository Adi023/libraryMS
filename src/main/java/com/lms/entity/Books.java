package com.lms.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "books")
public class Books {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ISBN;
	
	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publicationDate")
	private LocalDate publicationDate;

	@Column(name = "totalQuantity")
	private Long totalQuantity;

	@Column(name = "availableQuantity")
	private Long availableQuantity;
	
	@CreationTimestamp 
	@Column(name="timestamp")
	private Timestamp  ts;
	 
	@UpdateTimestamp
	@Column(name="updatedTime")
	private Timestamp updatedTime;

}
