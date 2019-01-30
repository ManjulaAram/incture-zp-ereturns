package com.incture.zp.ereturns.repositoriesimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserNameDto;
import com.incture.zp.ereturns.model.UserName;
import com.incture.zp.ereturns.repositories.UserNameRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.SequenceNumberGen;

@Repository
public class UserNameRepositoryImpl implements UserNameRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ImportExportUtil importExportUtil;

	public String getNextSeqNumber(String referenceCode, int noOfDigits) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(referenceCode, noOfDigits,
				sessionFactory.getCurrentSession());
	}
	
	@Override
	public ResponseDto addUserName(UserName userName) {
		ResponseDto responseDto = new ResponseDto();
		String userNameId = getNextSeqNumber(new GetReferenceData().execute("UN"), 6);
		if (userName.getUserId() == null || userName.getUserId().equals("")) {
			userName.setUserId(userNameId);
		}

		sessionFactory.getCurrentSession().saveOrUpdate(userName);
		responseDto.setMessage(userName.getUserId());
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;

	}
	
	public UserNameDto getUserById(String userId) {
		UserNameDto userNameDto = new UserNameDto();
		String queryStr = "select u from UserName u WHERE u.userId=:userId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<UserName> userList = query.list();
		for (UserName userName : userList) {
			userNameDto = importExportUtil.exportUserNameDto(userName);
		}
		return userNameDto;
	}

	public boolean isUserExist(String userId) {
		boolean flag = false;
		UserNameDto userNameDto = getUserById(userId);
		if (userNameDto != null && userNameDto.getUserId() != null && !(userNameDto.getUserId().equals(""))) {
			flag = true;
		}
		return flag;

	}

}
