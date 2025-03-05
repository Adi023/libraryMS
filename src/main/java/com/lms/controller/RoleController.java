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

import com.lms.dto.RoleDto;
import com.lms.exception.ResourceNotFound;
import com.lms.services.RoleServices;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleServices roleServices;

    public RoleController(RoleServices roleServices) {
        this.roleServices = roleServices;
    }

    // Add a new role
    @PostMapping
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto) {
        logger.info("Received request to add role: {}", roleDto.getRoleName());

        try {
            RoleDto savedRole = roleServices.addRole(roleDto);
            logger.info("Role added successfully: {}", savedRole.getRoleName());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid role data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Error while adding role: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing role
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable("roleId") Integer roleId, @RequestBody RoleDto roleDto) {
        logger.info("Received request to update role with ID: {}", roleId);

        try {
            roleDto.setRoleID(roleId); // Ensure ID consistency
            RoleDto updatedRole = roleServices.updateRole(roleDto);
            logger.info("Role updated successfully: {}", updatedRole.getRoleName());
            return ResponseEntity.ok(updatedRole);
        } catch (ResourceNotFound e) {
            logger.warn("Role not found: ID {}", roleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error while updating role: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get a role by ID
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable("roleId") Integer roleId) {
        logger.info("Received request to fetch role with ID: {}", roleId);

        try {
            RoleDto roleDto = roleServices.getRoleById(roleId);
            logger.info("Role retrieved successfully: {}", roleDto.getRoleName());
            return ResponseEntity.ok(roleDto);
        } catch (ResourceNotFound e) {
            logger.warn("Role not found: ID {}", roleId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error while fetching role: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get all roles
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        logger.info("Received request to fetch all roles.");

        try {
            List<RoleDto> roles = roleServices.getAllRole();
            if (roles.isEmpty()) {
                logger.warn("No roles found in the database.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roles);
            }
            logger.info("Retrieved {} roles from the database.", roles.size());
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error while fetching roles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
