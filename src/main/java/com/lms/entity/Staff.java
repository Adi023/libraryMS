package com.lms.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name= "staff")
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long staffId;
	
	@Column(name="member")
	private Long memberId;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="member",insertable=false,updatable = false)
	private Members member;
	
	@Column(nullable = false)
	private LocalDate dateOfHire;
	
	@Column(name="salary")
	private long salary;
	  
	@Column(name="specialization")
	private String specialization;
	
	@CreationTimestamp 
	@Column(name = "created_at", updatable = false)
	private Timestamp  ts;
	 
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedTime;

}
