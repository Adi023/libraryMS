package com.lms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lms.dto.MembersDto;
import com.lms.entity.Members;
import com.lms.exception.ResourceNotFound;
import com.lms.repository.MembersRepository;
import com.lms.services.MembersServices;

@Service
public class MembersServicesImpl implements MembersServices {

	private static final Logger logger = LoggerFactory.getLogger(MembersServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MembersRepository memberRepo;
	
	@Override
	public MembersDto addMember(MembersDto membersDto) {
		
		if (membersDto == null) {
			throw new IllegalArgumentException("Members DTO cannot be null");
		}
		try {
			Members members = dtoToEntity(membersDto);
			Members m = memberRepo.save(members);
			return entityToDto(m);
			
		} catch (Exception e) {
			e.printStackTrace();
			// Log the exception instead of printing the stack trace
			logger.error("Error while saving Members: {}", e.getMessage(), e);
			throw new IllegalArgumentException("Members DTO cannot be null");
		}
	}

	@Override
	public MembersDto updateMember(MembersDto membersDto) {
		
		if (membersDto == null) {
			throw new IllegalArgumentException("Member DTO cannot be null");
		}
		try {
			Members members = memberRepo.findById(membersDto.getMemberId())
					.orElseThrow(() -> new ResourceNotFound("Member not found with ID: " + membersDto.getMemberId()));
			
			members.setMemberName(membersDto.getMemberName());
			members.setAddress(membersDto.getAddress());
			members.setActive(membersDto.isActive());
			members.setEmail(membersDto.getEmail());
			members.setGender(membersDto.getGender());
			members.setMembershipDate(membersDto.getMembershipDate());
			members.setMembershipType(membersDto.getMembershipType());
			members.setPhoneNumber(membersDto.getPhoneNumber());
			members.setRoleId(membersDto.getRoleId());
			
			Members updatedMembers=memberRepo.save(members);
			logger.info("Member updated successfully: Members Id {}", updatedMembers.getMemberId());
			
			return entityToDto(updatedMembers);

		} catch (ResourceNotFound e) {
	        logger.warn("Update failed - Member not found: Member Id {}", membersDto.getMemberId());
	        throw e; // Re-throw the ResourceNotFound exception

	    } catch (Exception e) {
	        logger.error("Unexpected error while updating Member: {}", e.getMessage(), e);
	        throw new RuntimeException("An error occurred while updating the Member", e);
	    }
	}

	@Override
	public MembersDto getMemberById(Long membersId) {
		
		if (membersId == null) {
			throw new IllegalArgumentException("Member Id is null");
		}

		Members members = memberRepo.findById(membersId).orElseThrow(() -> new ResourceNotFound("Member not found with this ID"));
		logger.info("Member found : Member ID {}", membersId);
		return entityToDto(members);
	}

	@Override
	public List<MembersDto> getAllMember() {
		
		try {
			List<Members> members=memberRepo.findAll();
			
			if(members.isEmpty()) {
				logger.warn("No members found in the database");
				throw new ResourceNotFound("No members available.");
			}
			
			logger.info("Retrived {} members from database.",members.size());
			return members.stream().map(this::entityToDto).collect(Collectors.toList());
			
		}catch(Exception e) {
			logger.error("Error while fetching members: {}", e.getMessage(),e);
			throw new RuntimeException("An error occurred while retrieving members", e);
		}
	}

//	@Override
//	public String deleteMember(Long membersId) {
//		
//		return null;
//	}
	
	public MembersDto entityToDto(Members members) {
		return modelMapper.map(members, MembersDto.class);
	}
	
	public Members dtoToEntity(MembersDto membersDto) {
		return modelMapper.map(membersDto, Members.class);
	}

}
