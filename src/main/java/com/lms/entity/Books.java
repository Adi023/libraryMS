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
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publicationDate")
	private LocalDate publicationDate;

	@Column(nullable = false)
	private Long totalQuantity;

	@Column(name = "availableQuantity")
	private Long availableQuantity;
	
	@CreationTimestamp 
	@Column(name = "created_at", updatable = false)
	private Timestamp  ts;
	 
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedTime;

}
