package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.Role;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.ImportExportUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImportExportUtil importExportUtil;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public ResponseDto addUser(UserDto userDto) {
		return userRepository.addUser(importExportUtil.importUserDto(userDto));
	}

	@Override
	public UserDto getUserById(String id) {
		User user = userRepository.getUserById(id);
		UserDto userDto = importExportUtil.exportUserDto(user);
		Set<RoleDto> setRole = new HashSet<RoleDto>();
		for (Role role : user.getRoleDetails()) {
			setRole.add(importExportUtil.exportRoleDto(role));
		}
		userDto.setSetRole(setRole);
		return userDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getUserRole(String userId) {

		List<Role> listRoles = new ArrayList<Role>();
		String queryStr = "select r from User u join u.roleDetails r" + " where u.userId =:userId";

		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("userId", userId);
		listRoles = query.list();

		return listRoles;
	}

}
