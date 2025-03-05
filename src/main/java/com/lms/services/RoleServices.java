package com.lms.services;

import java.util.List;
import com.lms.dto.RoleDto;

public interface RoleServices {
	
	public RoleDto addRole(RoleDto roleDto);
	public RoleDto updateRole(RoleDto roleDto);
	public RoleDto getRoleById(Integer roleId);
	public List<RoleDto> getAllRole();
//	public String deleteRole(Integer roleId);

}
