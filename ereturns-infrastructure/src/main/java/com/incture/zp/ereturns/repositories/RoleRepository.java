package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.RoleDto;

public interface RoleRepository {

	/*public ResponseDto addRole(Role role);
	
	public ResponseDto updateRole(Role role);
	
	public ResponseDto deleteRole(Role role);*/
	
	public RoleDto getRoleById(String id);
}
