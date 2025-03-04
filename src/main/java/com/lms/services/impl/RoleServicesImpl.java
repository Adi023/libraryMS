package com.lms.services.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.MembersDto;
import com.lms.dto.RoleDto;
import com.lms.entity.Members;
import com.lms.entity.Role;
import com.lms.repository.RoleRepository;
import com.lms.services.RoleServices;

@Service
public class RoleServicesImpl implements RoleServices {

	private static final Logger logger = LoggerFactory.getLogger(BooksServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public RoleDto addRole(RoleDto roleDto) {
		
		return null;
	}

	@Override
	public RoleDto updateMember(RoleDto roleDto) {
		
		return null;
	}

	@Override
	public RoleDto getMemberById(Integer roleId) {
		
		return null;
	}

	@Override
	public List<RoleDto> getAllMember() {
		
		return null;
	}

	@Override
	public String deleteMember(Integer roleId) {
		
		return null;
	}
	
	public RoleDto entityToDto(Role role) {
		return modelMapper.map(role, RoleDto.class);
	}
	
	public Role dtoToEntity(RoleDto roleDto) {
		return modelMapper.map(roleDto, Role.class);
	}

}
