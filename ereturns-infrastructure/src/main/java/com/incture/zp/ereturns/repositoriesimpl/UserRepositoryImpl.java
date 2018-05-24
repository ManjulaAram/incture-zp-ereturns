package com.incture.zp.ereturns.repositoriesimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.Role;
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
		if (user.getUserId() == null || user.getUserId().equals("")) {
			user.setUserId(userId);
			responseDto.setMessage("User " + userId + " Created Successfully");
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		responseDto.setMessage("User " + user.getUserId() + " Updated Successfully");
		responseDto.setStatus("OK");
		responseDto.setCode("00");
		return responseDto;
	}

	@Override
	public User getUserById(String id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		
		return user;
	}

	@Override
	public ResponseDto delete(User user) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE User WHERE userId=:userId";
		if (!ServiceUtil.isEmpty(user)) {
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setParameter("userId", user.getUserId());
			

			int result = query.executeUpdate();
			if (result > 0) {
				responseDto.setMessage("Deleted Successfully");
				responseDto.setStatus("OK");
			} else {
				responseDto.setMessage("Delete Unsuccessfully");
				responseDto.setStatus("ERROR");
			}
		}
		return responseDto;
	}

	public ResponseDto updateMobileToken(User user) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "UPDATE User SET mobileToken=:mobileToken, sapAccessToken=:sapAccessToken, "
				+ "sapRefreshToken=:sapRefreshToken WHERE userId=:userId";
		if (!ServiceUtil.isEmpty(user)) {
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setParameter("mobileToken", user.getMobileToken());
			query.setParameter("sapAccessToken", user.getSapAccessToken());
			query.setParameter("sapRefreshToken", user.getSapRefreshToken());

			int result = query.executeUpdate();
			if (result > 0) {
				responseDto.setCode("01");
				responseDto.setMessage("Updated Successfully");
				responseDto.setStatus("OK");
			} else {
				responseDto.setCode("02");
				responseDto.setMessage("Updated Unsuccessfully");
				responseDto.setStatus("ERROR");
			}
		}
		return responseDto;

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
