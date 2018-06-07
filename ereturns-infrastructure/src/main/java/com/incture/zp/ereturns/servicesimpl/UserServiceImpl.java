package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.RoleDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.dto.UserMailDto;
import com.incture.zp.ereturns.model.Role;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.RestInvoker;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImportExportUtil importExportUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public ResponseDto addUser(UserDto userDto) {
		return userRepository.addUser(importExportUtil.importUserDto(userDto));
	}

	@Override
	public UserDto getUserById(String id) {
		LOGGER.error("User by id getting service:" + id);
		User user = userRepository.getUserById(id);
		LOGGER.error("User by id getting service22:" + user.getEmail());
		UserDto userDto = importExportUtil.exportUserDto(user);
		Set<RoleDto> setRole = new HashSet<RoleDto>();
		for (Role role : user.getRoleDetails()) {
			setRole.add(importExportUtil.exportRoleDto(role));
		}
		userDto.setSetRole(setRole);
		return userDto;
	}

	@Override
	public List<RoleDto> getUserRole(String userId) {
		List<RoleDto> listRoles = new ArrayList<RoleDto>();
		List<Role> roles = userRepository.getUserRole(userId);
		for (Role role : roles) {
			listRoles.add(importExportUtil.exportRoleDto(role));
		}
		return listRoles;
	}

	@Override
	public ResponseDto updateMobileToken(UserDto userDto) {
		ResponseDto responseDto = userRepository.updateMobileToken(importExportUtil.importUserDto(userDto));
		return responseDto;
	}

	@Override
	public UserMailDto GetMailIdByRole(String role) {

		UserMailDto userMailDto = new UserMailDto();
		String url = "https://auxes3rr8.accounts.ondemand.com/service/scim/Users";
		String username = "T000002";
		String password = "Incture@10";
		String path = "";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path = "?filter=groups%20eq%20%27" + role + "%27";
		String response = restInvoker.getData(path);

		JSONObject responseObject = new JSONObject(response);

		JSONArray resourcesArray = new JSONArray();
		resourcesArray = responseObject.getJSONArray("Resources");
		List<String> emailList = new ArrayList<String>();
		for (int counter = 0; counter < resourcesArray.length(); counter++) {
			JSONObject resourceObject = new JSONObject();
			resourceObject = (JSONObject) resourcesArray.get(counter);
			JSONArray mailArray = new JSONArray();
			mailArray = (JSONArray) resourceObject.get("emails");
			for (int mailCounter = 0; mailCounter < mailArray.length(); mailCounter++) {
				JSONObject mailObject = new JSONObject();
				mailObject = (JSONObject) mailArray.get(counter);
				String email = "";
				email = mailObject.get("value").toString();

				emailList.add(email.toString());
			}
		}
		userMailDto.setListOfMailIds(emailList);
		return userMailDto;
	}

}
