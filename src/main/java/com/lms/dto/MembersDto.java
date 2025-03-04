package com.lms.dto;

import java.time.LocalDate;

import com.lms.entity.Role;
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
public class MembersDto {

	private Long memberId;
	private String memberName;
	private String email;
	private String phoneNumber;
	private String address;
	private String gender;
	private boolean isActive;
	private LocalDate membershipDate;
	private String membershipType;
	private int roleId;
	private Role role;
}
