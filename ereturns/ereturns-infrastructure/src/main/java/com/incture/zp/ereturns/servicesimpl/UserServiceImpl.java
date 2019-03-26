package com.incture.zp.ereturns.servicesimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.EmailDto;
import com.incture.zp.ereturns.dto.IdpGroupDto;
import com.incture.zp.ereturns.dto.IdpUserDetailsDto;
import com.incture.zp.ereturns.dto.IdpUserDto;
import com.incture.zp.ereturns.dto.IdpUserIdDto;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.services.UserService;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.ReadCustomerExcelUtil;
import com.incture.zp.ereturns.utils.RestInvoker;
import com.incture.zp.ereturns.utils.ServiceUtil;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ImportExportUtil importExportUtil;
	
	@Autowired
	ReadCustomerExcelUtil readCustomerExcelUtil;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	String destination;
	String user;
	String pwd;
	
	public UserServiceImpl() {
		DestinationConfiguration destConfiguration = ServiceUtil.getDest(EReturnConstants.SCIM_DESTINATION);
		destination = destConfiguration.getProperty(EReturnConstants.SCIM_DESTINATION_URL);
		user = destConfiguration.getProperty(EReturnConstants.SCIM_DESTINATION_USER);
		pwd = destConfiguration.getProperty(EReturnConstants.SCIM_DESTINATION_PWD);
		
//		destination = EReturnConstants.SCIM_DESTINATION_URL;
//		user = EReturnConstants.SCIM_DESTINATION_USER;
//		pwd = EReturnConstants.SCIM_DESTINATION_PWD;

	}
	

	@Override
	public ResponseDto addUser(UserDto userDto) {
		return userRepository.addUser(importExportUtil.importUserDto(userDto));
	}

	@Override
	public UserDto getUserById(String id) {
		User user = userRepository.getUserById(id);
		UserDto userDto = new UserDto();
		if(user != null) {
			userDto = importExportUtil.exportUserDto(user);
		}
		return userDto;
	}

	@Override
	public EmailDto getEmailByRole(String role, String createdBy) {
		EmailDto emailDto = new EmailDto();
		
		String url = destination;
		String username = user;
		String password = pwd;
		String path = "";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path = "?filter=groups%20eq%20%27" + role + "%27";
		String response = restInvoker.getData(path);

		JSONObject responseObject = new JSONObject(response);
		StringBuilder sb = new StringBuilder();
		JSONArray resourcesArray = new JSONArray();
		resourcesArray = responseObject.getJSONArray(EReturnConstants.IDP_RESOURCES);
		List<String> emailList = new ArrayList<String>();
		for (int counter = 0; counter < resourcesArray.length(); counter++) {
			JSONObject resourceObject = new JSONObject();
			resourceObject = (JSONObject) resourcesArray.get(counter);
			JSONArray mailArray = new JSONArray();
			mailArray = (JSONArray) resourceObject.get(EReturnConstants.IDP_EMAILS);
			for (int mailCounter = 0; mailCounter < mailArray.length(); mailCounter++) {
				JSONObject mailObject = new JSONObject();
				mailObject=(JSONObject)mailArray.get(mailCounter);
				String email = "";
				email = mailObject.get(EReturnConstants.IDP_VALUE).toString();
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
//		LOGGER.error("Idp Email by Role:"+sb.toString());
		if(role != null && !(role.equals(""))) {
			emailDto.setEmail(sb.toString());
		} 
		if(role.equalsIgnoreCase("NA")){
			IdpUserDetailsDto details = getIdpUserDetailsById(createdBy);
			emailDto.setEmail(details.getEmail());
		}
		return emailDto;
	}
	
	@Override
	public IdpUserIdDto getUserIdByRole(String role) {
		IdpUserIdDto idpUserIdDto = new IdpUserIdDto();
		String url = destination;
		String username = user;
		String password = pwd;
		String path = "";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path = "?filter=groups%20eq%20%27" + role + "%27";
		String response = restInvoker.getData(path);
		List<String> idList = new ArrayList<String>();
		JSONObject responseObject = new JSONObject(response);
		JSONArray resourcesArray = new JSONArray();
		resourcesArray = responseObject.getJSONArray(EReturnConstants.IDP_RESOURCES);
		for (int counter = 0; counter < resourcesArray.length(); counter++) {
			JSONObject resourceObject = new JSONObject();
			resourceObject = (JSONObject) resourcesArray.get(counter);
			String user = resourceObject.get(EReturnConstants.IDP_ID).toString();
			idList.add(user);
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < idList.size() ; i++) {
			String userId = idList.get(i);
			sb.append(userId);
			if(i < (idList.size()-1))
			{
				sb.append(",");
			}
		}
		idpUserIdDto.setUserId(sb.toString());
		return idpUserIdDto;
	}

	@Override
	public List<String> getUsersByRole(String role) {
		String url = destination;
		String username = user;
		String password = pwd;
		String path = "";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path = "?filter=groups%20eq%20%27" + role + "%27";
		String response = restInvoker.getData(path);
		List<String> idList = new ArrayList<String>();
		JSONObject responseObject = new JSONObject(response);
		JSONArray resourcesArray = new JSONArray();
		resourcesArray = responseObject.getJSONArray(EReturnConstants.IDP_RESOURCES);
		for (int counter = 0; counter < resourcesArray.length(); counter++) {
			JSONObject resourceObject = new JSONObject();
			resourceObject = (JSONObject) resourcesArray.get(counter);
			String user = resourceObject.get(EReturnConstants.IDP_ID).toString();
			idList.add(user);
		}
		return idList;
	}

	@Override
	public String getUserNameById(String userId) {
		StringBuilder userName = new StringBuilder();
		String url = destination;
		String username = user;
		String password = pwd;
		String path="";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path="/"+userId;
		String response = restInvoker.getData(path);

		JSONObject responseObject=new JSONObject(response);
		if(responseObject.get(EReturnConstants.IDP_NAME) != null && !(responseObject.get(EReturnConstants.IDP_NAME).equals("")))
		{
			JSONObject nameObject=new JSONObject();
			nameObject=(JSONObject)responseObject.get(EReturnConstants.IDP_NAME);
			userName.append(nameObject.get(EReturnConstants.IDP_GIVEN_NAME)).append(" ");
			userName.append(nameObject.get(EReturnConstants.IDP_FAMILY_NAME));
		}
		return userName.toString();
	}

	@Override
	public IdpUserDetailsDto getIdpUserDetailsById(String userId) {
		IdpUserDetailsDto idpUserIdDto = new IdpUserDetailsDto();
		StringBuilder userName = new StringBuilder();
		String url = destination;
		String username = user;
		String password = pwd;
		String path="";
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		path="/"+userId;
		String response = restInvoker.getData(path);

		JSONObject responseObject=new JSONObject(response);
		if(responseObject != null) {
			if(responseObject.get(EReturnConstants.IDP_NAME) != null && !(responseObject.get(EReturnConstants.IDP_NAME).equals("")))
			{
				JSONObject nameObject=new JSONObject();
				nameObject=(JSONObject)responseObject.get(EReturnConstants.IDP_NAME);
				userName.append(nameObject.get(EReturnConstants.IDP_GIVEN_NAME)).append(" ");
				userName.append(nameObject.get(EReturnConstants.IDP_FAMILY_NAME));
			}
			idpUserIdDto.setUserName(userName.toString());
			if(responseObject.get(EReturnConstants.IDP_EMAILS) != null && !(responseObject.get(EReturnConstants.IDP_EMAILS).equals("")))
			{
				JSONArray mailArray = new JSONArray();
				mailArray = (JSONArray) responseObject.get(EReturnConstants.IDP_EMAILS);
				for (int mailCounter = 0; mailCounter < mailArray.length(); mailCounter++) {
					JSONObject mailObject = new JSONObject();
					mailObject=(JSONObject)mailArray.get(mailCounter);
					String email = "";
					email = mailObject.get(EReturnConstants.IDP_VALUE).toString();
					idpUserIdDto.setEmail(email);
				} 
			}
			if(responseObject.get(EReturnConstants.IDP_GROUPS) != null && !(responseObject.get(EReturnConstants.IDP_GROUPS).equals(""))) {
				JSONArray roleArray = new JSONArray();
				roleArray = (JSONArray) responseObject.get(EReturnConstants.IDP_GROUPS);
				for (int roleCounter = 0; roleCounter < roleArray.length(); roleCounter++) {
					JSONObject roleObject = new JSONObject();
					roleObject=(JSONObject) roleArray.get(roleCounter);
					String role = "";
					role = roleObject.get(EReturnConstants.IDP_VALUE).toString();
					if(role != null && !(role.equals(""))) {
						if(role.contains("PRINCIPAL_")) {
							idpUserIdDto.setRole(role);
							break;
						} else if(role.contains("ZP-APPROVER_")) {
							idpUserIdDto.setRole(role);	
							break;
						} else if(role.equalsIgnoreCase("Requestor")){
							idpUserIdDto.setRole("Requestor");
							break;
						} 
					}
				} 
			}
		}
		return idpUserIdDto;
	}

	public ResponseDto loginUser() {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(String.valueOf(HttpStatus.SC_OK));
		responseDto.setStatus(String.valueOf(HttpStatus.SC_OK));
		return responseDto;
	}


	@Override
	public UserDto getUserDetailsById(String id) {
		return userRepository.getUserDetailsById(id);
	}
	
	public ResponseDto createUserOnIdp(IdpUserDto idpUserDto) {
		ResponseDto responseDto = new ResponseDto();
		String pUser = "";
		
		JSONObject userObj = new JSONObject();

		userObj.put(EReturnConstants.IDP_SEND_MAIL, "true");
		userObj.put(EReturnConstants.IDP_MAIL_VERIFIED, "false");
		userObj.put(EReturnConstants.IDP_USER_NAME, idpUserDto.getLoginName());
		userObj.put(EReturnConstants.IDP_USER_TYPE, idpUserDto.getUserType());
		userObj.put(EReturnConstants.IDP_ACTIVE, Boolean.TRUE);

		JSONObject nameObj = new JSONObject();
		nameObj.put(EReturnConstants.IDP_GIVEN_NAME, idpUserDto.getFirstName());
		nameObj.put(EReturnConstants.IDP_FAMILY_NAME, idpUserDto.getLastName());
		nameObj.put(EReturnConstants.IDP_SALUTATION, ""); //Salutation

		JSONArray emailArry = new JSONArray();

		JSONObject emailObj = new JSONObject();
		emailObj.put(EReturnConstants.IDP_VALUE, idpUserDto.getEmail());

		emailArry.put(emailObj);

		JSONArray groupArry = new JSONArray();

		if(idpUserDto.getGroup() != null && !(idpUserDto.getGroup().equals(""))) {
			String[] arry = idpUserDto.getGroup().split(",");
			
			for(int i = 0 ; i < arry.length ; i++) {
				JSONObject groupObj = new JSONObject();
				groupObj.put(EReturnConstants.IDP_VALUE, arry[i]);

				groupArry.put(groupObj);
			}
		}

		JSONObject divisionObj = new JSONObject();
		divisionObj.put(EReturnConstants.IDP_DIVISION, idpUserDto.getDivision());
		
		userObj.put(EReturnConstants.IDP_EMAILS, emailArry);
		userObj.put(EReturnConstants.IDP_NAME, nameObj);
		userObj.put(EReturnConstants.IDP_GROUPS, groupArry);
		userObj.put(EReturnConstants.IDP_DIVISION_OBJ, divisionObj);
		
		LOGGER.error("Idp User creation input:"+userObj);

		String url = destination;
		String username = user;
		String password = pwd;
		
		RestInvoker restInvoker = new RestInvoker(url, username, password);
		String response = restInvoker.postDataToSCIM("", userObj.toString());

//		LOGGER.error("Idp User creation output:"+response);
		JSONObject outputObj = new JSONObject(response);
		

		pUser = outputObj.optString(EReturnConstants.IDP_ID);
		responseDto.setCode("00");
		responseDto.setMessage(pUser);
		responseDto.setStatus("SUCCESS");
		
		return responseDto;
	}

	public ResponseDto createGroupOnIdp(IdpGroupDto idpGroupDto) {
		ResponseDto responseDto = new ResponseDto();
		String pUser = "";
		
		JSONObject groupObj = new JSONObject();

		groupObj.put(EReturnConstants.IDP_GROUP_DISPLAY_NAME, idpGroupDto.getGroupDisplayName());

		JSONObject urnObj = new JSONObject();
		urnObj.put(EReturnConstants.IDP_NAME, idpGroupDto.getGroupName());
		urnObj.put(EReturnConstants.IDP_GROUP_DESC, idpGroupDto.getGroupDesc());

		groupObj.put(EReturnConstants.IDP_GROUP_URN, urnObj);
		LOGGER.error("Idp Group creation input:"+groupObj);

		String url = destination;
		String username = user;
		String password = pwd;
		
		String groupUrl = url.replace("Users", "Groups");
		RestInvoker restInvoker = new RestInvoker(groupUrl, username, password);
		String response = restInvoker.postDataToSCIM("", groupObj.toString());

		JSONObject outputObj = new JSONObject(response);

		pUser = outputObj.optString(EReturnConstants.IDP_GROUP_DISPLAY_NAME);
		responseDto.setCode("00");
		responseDto.setMessage(pUser);
		responseDto.setStatus("SUCCESS");
		
		return responseDto;
	}

	public ResponseDto createUserFromExcel(String filePath) {
		ResponseDto responseDto = new ResponseDto();
		List<IdpUserDto> users = readCustomerExcelUtil.readUserExcel(filePath);
		for(IdpUserDto dto : users) {
			createUserOnIdp(dto);
		}
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}
	
	public ResponseDto createGroupFromExcel(String filePath) {
		ResponseDto responseDto = new ResponseDto();
		List<IdpGroupDto> groups = readCustomerExcelUtil.readGroupExcel(filePath);
		for(IdpGroupDto dto : groups) {
			createGroupOnIdp(dto);
		}
		responseDto.setCode("00");
		responseDto.setMessage("SUCCESS");
		responseDto.setStatus("SUCCESS");
		return responseDto;
	}
	
}
