package com.lms.services.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.RoleDto;
import com.lms.dto.StaffDto;
import com.lms.entity.Role;
import com.lms.entity.Staff;
import com.lms.repository.StaffRepository;
import com.lms.services.StaffServices;

@Service
public class StaffServicesImpl implements StaffServices {

	private static final Logger logger = LoggerFactory.getLogger(BooksServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Override
	public StaffDto addStaff(StaffDto staffDto) {
		
		return null;
	}

	@Override
	public StaffDto updateStaff(StaffDto staffDto) {
		
		return null;
	}

	@Override
	public StaffDto getStaffById(Long staffId) {
		
		return null;
	}

	@Override
	public List<StaffDto> getAllStaff() {
		
		return null;
	}

	@Override
	public String deleteStaff(Long staffId) {
		
		return null;
	}
	
	public StaffDto entityToDto(Staff staff) {
		return modelMapper.map(staff, StaffDto.class);
	}
	
	public Staff dtoToEntity(StaffDto staffDto) {
		return modelMapper.map(staffDto, Staff.class);
	}

}
