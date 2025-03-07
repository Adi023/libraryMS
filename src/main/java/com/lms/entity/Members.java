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
@Table(name= "members")
public class Members {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memberId;
	
	@Column(nullable = false)
	private String memberName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(name = "address")
	private String address;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(name = "isActive")
	private boolean isActive;
	
	@Column(name = "membershipDate")
	private LocalDate membershipDate;
	
	@Column(name = "membershipType")
	private String membershipType;
	
	@Column(name = "roleId")
	private int roleId;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "roleId",insertable=false,updatable=false)
	private Role role;
	
	@CreationTimestamp 
	@Column(name = "created_at", updatable = false)
	private Timestamp  ts;
	 
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedTime;

}
