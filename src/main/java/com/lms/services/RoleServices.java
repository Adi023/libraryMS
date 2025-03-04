package com.lms.services;

import java.util.List;
import com.lms.dto.RoleDto;

public interface RoleServices {
	
	public RoleDto addRole(RoleDto roleDto);
	public RoleDto updateMember(RoleDto roleDto);
	public RoleDto getMemberById(Integer roleId);
	public List<RoleDto> getAllMember();
	public String deleteMember(Integer roleId);

}
