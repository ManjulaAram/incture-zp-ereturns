package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.model.Role;
import com.incture.zp.ereturns.repositories.RoleRepository;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;



	@Override
	public RoleDto getRoleById(String id) {
		Role role = (Role)sessionFactory.getCurrentSession().get(Role.class, id);
		RoleDto roleDto = importExportUtil.exportRoleDto(role);
		return roleDto;
	}

}
