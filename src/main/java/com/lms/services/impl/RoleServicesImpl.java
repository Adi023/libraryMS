package com.lms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.RoleDto;
import com.lms.entity.Role;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.RoleRepository;
import com.lms.services.RoleServices;

@Service
public class RoleServicesImpl implements RoleServices {

	private static final Logger logger = LoggerFactory.getLogger(RoleServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public RoleDto addRole(RoleDto roleDto) {

		if (roleDto == null) {
			throw new IllegalArgumentException("Role DTO cannot be null");
		}
		try {
			Role role = dtoToEntity(roleDto);
			Role r = roleRepo.save(role);
			return entityToDto(r);

		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception instead of printing the stack trace
			logger.error("Error while saving Role: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Role DTO cannot be null");
		}
	}

	@Override
	public RoleDto updateRole(RoleDto roleDto) {

		if (roleDto == null) {
			throw new IllegalArgumentException("Role DTO cannot be null");
		}
		try {
			Role role = roleRepo.findById(roleDto.getRoleID())
					.orElseThrow(() -> new ResourceNotFound("Role not found with ID: " + roleDto.getRoleID()));

			role.setRoleName(roleDto.getRoleName());

			Role updatedRole = roleRepo.save(role);
			logger.info("Role updated successfully: Role Id {}", updatedRole.getRoleID());

			return entityToDto(updatedRole);

		} catch (ResourceNotFound e) {
			logger.warn("Update failed - Role not found: Role Id {}", roleDto.getRoleID());
			throw e; // Re-throw the ResourceNotFound exception

		} catch (Exception e) {
			logger.error("Unexpected error while updating Role: {}", e.getMessage(), e);
			throw new RuntimeException("An error occurred while updating the Role", e);
		}
	}

	@Override
	public RoleDto getRoleById(Integer roleId) {
		if (roleId == null) {
			throw new IllegalArgumentException("RoleId is null");
		}

		Role role = roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFound("Role not found with this ID"));
		logger.info("Role found : Role ID {}", roleId);
		return entityToDto(role);
	}

	@Override
	public List<RoleDto> getAllRole() {
		try {
			List<Role> role = roleRepo.findAll();

			if (role.isEmpty()) {
				logger.warn("No role found in the database");
				throw new ResourceNotFound("No role available.");
			}

			logger.info("Retrived {} role from database.", role.size());
			return role.stream().map(this::entityToDto).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("Error while fetching role: {}", e.getMessage(), e);
			throw new RuntimeException("An error occurred while retrieving role", e);
		}
	}

//	@Override
//	public String deleteRole(Integer roleId) {
//		
//		return null;
//	}

	public RoleDto entityToDto(Role role) {
		return modelMapper.map(role, RoleDto.class);
	}

	public Role dtoToEntity(RoleDto roleDto) {
		return modelMapper.map(roleDto, Role.class);
	}

}
