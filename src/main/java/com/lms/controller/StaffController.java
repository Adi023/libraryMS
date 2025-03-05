package com.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.StaffDto;
import com.lms.exception.ResourceNotFound;
import com.lms.services.StaffServices;

@RestController
@RequestMapping("/staff")
public class StaffController  {

	  private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
	    private final StaffServices staffServices;

	    public StaffController(StaffServices staffServices) {
	        this.staffServices = staffServices;
	    }

	    // Add new staff member
	    @PostMapping
	    public ResponseEntity<StaffDto> addStaff(@RequestBody StaffDto staffDto) {
	        logger.info("Received request to add staff: {}", staffDto.getStaffId());

	        try {
	            StaffDto savedStaff = staffServices.addStaff(staffDto);
	            logger.info("Staff added successfully: {}", savedStaff.getStaffId());
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedStaff);
	        } catch (IllegalArgumentException e) {
	            logger.warn("Invalid staff data: {}", e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        } catch (Exception e) {
	            logger.error("Error while adding staff: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Update an existing staff member
	    @PutMapping("/{staffId}")
	    public ResponseEntity<StaffDto> updateStaff(@PathVariable("staffId") Long staffId, @RequestBody StaffDto staffDto) {
	        logger.info("Received request to update staff with ID: {}", staffId);

	        try {
	            staffDto.setStaffId(staffId); // Ensure ID matches the path
	            StaffDto updatedStaff = staffServices.updateStaff(staffDto);
	            logger.info("Staff updated successfully: {}", updatedStaff.getStaffId());
	            return ResponseEntity.ok(updatedStaff);
	        } catch (ResourceNotFound e) {
	            logger.warn("Staff not found: ID {}", staffId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            logger.error("Error while updating staff: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Get staff member by ID
	    @GetMapping("/{staffId}")
	    public ResponseEntity<StaffDto> getStaffById(@PathVariable("staffId") Long staffId) {
	        logger.info("Received request to fetch staff with ID: {}", staffId);

	        try {
	            StaffDto staffDto = staffServices.getStaffById(staffId);
	            logger.info("Staff retrieved successfully: {}", staffDto.getStaffId());
	            return ResponseEntity.ok(staffDto);
	        } catch (ResourceNotFound e) {
	            logger.warn("Staff not found: ID {}", staffId);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } catch (Exception e) {
	            logger.error("Error while fetching staff: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    // Get all staff members
	    @GetMapping
	    public ResponseEntity<List<StaffDto>> getAllStaff() {
	        logger.info("Received request to fetch all staff members.");

	        try {
	            List<StaffDto> staffList = staffServices.getAllStaff();
	            if (staffList.isEmpty()) {
	                logger.warn("No staff members found in the database.");
	                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(staffList);
	            }
	            logger.info("Retrieved {} staff members from the database.", staffList.size());
	            return ResponseEntity.ok(staffList);
	        } catch (Exception e) {
	            logger.error("Error while fetching staff members: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

}
