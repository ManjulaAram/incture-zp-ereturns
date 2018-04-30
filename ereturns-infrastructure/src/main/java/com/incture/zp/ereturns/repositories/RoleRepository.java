package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Role;

public interface RoleRepository {

	public ResponseDto addRole(Role role);
	
	public ResponseDto updateRole(Role role);
	
	public ResponseDto deleteRole(Role role);
	
	public Role getRoleById(String id);
}
