package com.lms.services;

import java.util.List;
import com.lms.dto.StaffDto;

public interface StaffServices {

	public StaffDto addStaff(StaffDto staffDto);
	public StaffDto updateStaff(StaffDto staffDto);
	public StaffDto getStaffById(Long staffId);
	public List<StaffDto> getAllStaff();
//	public String deleteStaff(Long staffId);
}
