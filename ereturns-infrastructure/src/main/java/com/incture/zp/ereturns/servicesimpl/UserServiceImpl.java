package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.dto.EmailDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
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
		User user = userRepository.getUserById(id);
		UserDto userDto = importExportUtil.exportUserDto(user);
		return userDto;
	}

	@Override
	public EmailDto getEmailByRole(String role) {
		EmailDto emailDto = new EmailDto();
		String url = "https://auxes3rr8.accounts.ondemand.com/service/scim/Users";
		String username = "T000002";
		String password = "Incture@10";
		String path = "";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path = "?filter=groups%20eq%20%27" + role + "%27";
		String response = restInvoker.getData(path);

		JSONObject responseObject = new JSONObject(response);
		StringBuilder sb = new StringBuilder();
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
				mailObject=(JSONObject)mailArray.get(mailCounter);
				String email = "";
				email = mailObject.get("value").toString();
				emailList.add(email.toString());
			}
		}
		for(int i = 0 ; i < emailList.size() ; i++) {
			String email = emailList.get(i);
			sb.append(email);
			if(i < (emailList.size()-1))
			{
				sb.append(",");
			}
		}
		emailDto.setEmail(sb.toString());
		return emailDto;
	}

	@Override
	public String getUserNameById(String userId) {
		StringBuilder userName = new StringBuilder();
		String url ="https://auxes3rr8.accounts.ondemand.com/service/scim/Users";
		String username = "T000002";
		String password = "Incture@10";
		String path="";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path="/"+userId;
		String response = restInvoker.getData(path);

		JSONObject responseObject=new JSONObject(response);
		if(responseObject.get("name").toString()!="" && responseObject.get("name").toString()!=null)
		{
		JSONObject nameObject=new JSONObject();
		nameObject=(JSONObject)responseObject.get("name");
		userName.append(nameObject.get("givenName")).append(" ");
		userName.append(nameObject.get("familyName"));
		}
		LOGGER.error("user id"+userId);
		return userName.toString();
	}

}
