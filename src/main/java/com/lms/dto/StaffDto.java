package com.lms.dto;

import java.time.LocalDate;
import com.lms.entity.Members;
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
public class StaffDto {

	private Long staffId;
	private Long memberId;
	private Members member;
	private LocalDate dateOfHire;
	private long salary;
	private String specialization;
}