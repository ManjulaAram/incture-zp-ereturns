package com.incture.zp.ereturns.repositoriesimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.constants.EReturnConstants;
import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.dto.UserDto;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;
import com.incture.zp.ereturns.utils.ImportExportUtil;
import com.incture.zp.ereturns.utils.SequenceNumberGen;
import com.incture.zp.ereturns.utils.ServiceUtil;

@Repository()
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ImportExportUtil importExportUtil;

	public String getNextSeqNumber(String referenceCode, int noOfDigits) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(referenceCode, noOfDigits,
				sessionFactory.getCurrentSession());
	}

	@Override
	public ResponseDto addUser(User user) {
		ResponseDto responseDto = new ResponseDto();
		String userId = getNextSeqNumber(new GetReferenceData().execute("U"), 6);
		if (user.getIdpUserId() == null || user.getIdpUserId().equals("")) {
			user.setIdpUserId(userId);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		responseDto.setMessage(user.getIdpUserId());
		responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
		responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
		return responseDto;
	}

	@Override
	public User getUserById(String id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		
		return user;
	}

	@Override
	public UserDto getUserDetailsById(String id) {
		
		UserDto userDto = new UserDto();
		String queryStr = "SELECT u FROM User u WHERE u.idpUserId=:idpUserId";
		Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
		query.setParameter("idpUserId", id);
		
		@SuppressWarnings("unchecked")
		List<User> userList =  query.list();
		for(User user : userList) {
			userDto = importExportUtil.exportUserDto(user);
		}
		
		return userDto;
	}
	@Override
	public ResponseDto delete(User user) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE User WHERE idpUserId=:idpUserId";
		if (!ServiceUtil.isEmpty(user)) {
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setParameter("idpUserId", user.getIdpUserId());
			

			int result = query.executeUpdate();
			if (result > 0) {
				responseDto.setMessage("User Deleted Successfully");
				responseDto.setStatus(EReturnConstants.SUCCESS_STATUS);
				responseDto.setCode(EReturnConstants.SUCCESS_STATUS_CODE);
			} else {
				responseDto.setMessage("Delete Unsuccessfully");
				responseDto.setStatus(EReturnConstants.ERROR_STATUS);
				responseDto.setCode(EReturnConstants.ERROR_STATUS_CODE);
			}
		}
		return responseDto;
	}

}
