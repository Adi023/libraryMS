package com.lms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.MembersDto;
import com.lms.exception.ResourceNotFound;
import com.lms.services.MembersServices;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MembersController {

    private static final Logger logger = LoggerFactory.getLogger(MembersController.class);
    private final MembersServices membersServices;

    public MembersController(MembersServices membersServices) {
        this.membersServices = membersServices;
    }

    // Add a new member
    @PostMapping
    public ResponseEntity<MembersDto> addMember(@RequestBody MembersDto memberDto) {
        logger.info("Received request to add member: {}", memberDto.getMemberName());

        try {
            MembersDto savedMember = membersServices.addMember(memberDto);
            logger.info("Member added successfully: {}", savedMember.getMemberName());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid member data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Error while adding member: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing member
    @PutMapping("/{memberId}")
    public ResponseEntity<MembersDto> updateMember(@PathVariable("memberId") Long memberId, @RequestBody MembersDto memberDto) {
        logger.info("Received request to update member with ID: {}", memberId);

        try {
            memberDto.setMemberId(memberId); // Ensure ID matches the path
            MembersDto updatedMember = membersServices.updateMember(memberDto);
            logger.info("Member updated successfully: {}", updatedMember.getMemberName());
            return ResponseEntity.ok(updatedMember);
        } catch (ResourceNotFound e) {
            logger.warn("Member not found: ID {}", memberId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error while updating member: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get a member by ID
    @GetMapping("/{memberId}")
    public ResponseEntity<MembersDto> getMemberById(@PathVariable("memberId") Long memberId) {
        logger.info("Received request to fetch member with ID: {}", memberId);

        try {
            MembersDto memberDto = membersServices.getMemberById(memberId);
            logger.info("Member retrieved successfully: {}", memberDto.getMemberName());
            return ResponseEntity.ok(memberDto);
        } catch (ResourceNotFound e) {
            logger.warn("Member not found: ID {}", memberId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error while fetching member: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Get all members
    @GetMapping
    public ResponseEntity<List<MembersDto>> getAllMembers() {
        logger.info("Received request to fetch all members.");

        try {
            List<MembersDto> members = membersServices.getAllMember();
            if (members.isEmpty()) {
                logger.warn("No members found in the database.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(members);
            }
            logger.info("Retrieved {} members from the database.", members.size());
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            logger.error("Error while fetching members: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
