package com.lms.services;

import java.util.List;
import com.lms.dto.MembersDto;

public interface MembersServices {
	
	public MembersDto addMember(MembersDto member);
	public MembersDto updateMember(MembersDto member);
	public MembersDto getMemberById(Long membersId);
	public List<MembersDto> getAllMember();
	public String deleteMember(Long membersId);
}
