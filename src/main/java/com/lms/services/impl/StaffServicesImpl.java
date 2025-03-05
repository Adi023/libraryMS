package com.lms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.StaffDto;
import com.lms.entity.Staff;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.StaffRepository;
import com.lms.services.StaffServices;

@Service
public class StaffServicesImpl implements StaffServices {

	private static final Logger logger = LoggerFactory.getLogger(StaffServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StaffRepository staffRepo;
	
	@Override
	public StaffDto addStaff(StaffDto staffDto) {
		
		if (staffDto == null) {
			throw new IllegalArgumentException("Staff DTO cannot be null");
		}
		try {
			Staff staff = dtoToEntity(staffDto);
			Staff s = staffRepo.save(staff);
			return entityToDto(s);
			
		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception instead of printing the stack trace
			logger.error("Error while saving staff: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Staff DTO cannot be null");
		}
	}

	@Override
	public StaffDto updateStaff(StaffDto staffDto) {
		
		if (staffDto == null) {
			throw new IllegalArgumentException("Staff DTO cannot be null");
		}
		try {
			Staff staff = staffRepo.findById(staffDto.getStaffId())
					.orElseThrow(() -> new ResourceNotFound("Staff not found with ID: " + staffDto.getStaffId()));
			
			staff.setMemberId(staffDto.getMemberId());
			staff.setSalary(staffDto.getSalary());
			staff.setSpecialization(staffDto.getSpcialization());
			staff.setDateOfHire(staffDto.getDateOfHire());
			
			Staff updatedStaff=staffRepo.save(staff);
			logger.info("Staff updated successfully: Staff Id {}", updatedStaff.getStaffId());
			
			return entityToDto(updatedStaff);

		} catch (ResourceNotFound e) {
	        logger.warn("Update failed - Staff not found: Staff Id {}", staffDto.getStaffId());
	        throw e; // Re-throw the ResourceNotFound exception

	    } catch (Exception e) {
	        logger.error("Unexpected error while updating Staff: {}", e.getMessage(), e);
	        throw new RuntimeException("An error occurred while updating the Staff", e);
	    }
	}

	@Override
	public StaffDto getStaffById(Long staffId) {
		
		if (staffId == null) {
			throw new IllegalArgumentException("staffId is null");
		}

		Staff staff = staffRepo.findById(staffId).orElseThrow(() -> new ResourceNotFound("Staff not found with this ID"));
		logger.info("Staff found : staff ID {}", staffId);
		return entityToDto(staff);
	}

	@Override
	public List<StaffDto> getAllStaff() {
		
		try {
			List<Staff> staff=staffRepo.findAll();
			
			if(staff.isEmpty()) {
				logger.warn("No staff found in the database");
				throw new ResourceNotFound("No staff available.");
			}
			
			logger.info("Retrived {} staff from database.",staff.size());
			return staff.stream().map(this::entityToDto).collect(Collectors.toList());
			
		}catch(Exception e) {
			logger.error("Error while fetching staff: {}", e.getMessage(),e);
			throw new RuntimeException("An error occurred while retrieving staff", e);
		}
	}

//	@Override
//	public String deleteStaff(Long staffId) {
//		
//		return null;
//	}
	
	public StaffDto entityToDto(Staff staff) {
		return modelMapper.map(staff, StaffDto.class);
	}
	
	public Staff dtoToEntity(StaffDto staffDto) {
		return modelMapper.map(staffDto, Staff.class);
	}

}
