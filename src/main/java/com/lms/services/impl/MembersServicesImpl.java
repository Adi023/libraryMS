package com.lms.services.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.CategoryDto;
import com.lms.dto.MembersDto;
import com.lms.entity.Category;
import com.lms.entity.Members;
import com.lms.repository.MembersRepository;
import com.lms.services.MembersServices;

@Service
public class MembersServicesImpl implements MembersServices {

	private static final Logger logger = LoggerFactory.getLogger(BooksServicesImpl.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MembersRepository memberRepo;
	
	@Override
	public MembersDto addMember(MembersDto member) {
		
		return null;
	}

	@Override
	public MembersDto updateMember(MembersDto member) {
		
		return null;
	}

	@Override
	public MembersDto getMemberById(Long membersId) {
		
		return null;
	}

	@Override
	public List<MembersDto> getAllMember() {
		
		return null;
	}

	@Override
	public String deleteMember(Long membersId) {
		
		return null;
	}
	
	public MembersDto entityToDto(Members members) {
		return modelMapper.map(members, MembersDto.class);
	}
	
	public Members dtoToEntity(MembersDto membersDto) {
		return modelMapper.map(membersDto, Members.class);
	}

}
