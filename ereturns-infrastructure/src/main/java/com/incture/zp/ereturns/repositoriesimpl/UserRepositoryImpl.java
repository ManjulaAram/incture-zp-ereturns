package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public ResponseDto addUser(UserDto userDto) {
		ResponseDto responseDto = new ResponseDto();
		String userId = getNextSeqNumber(new GetReferenceData().execute("U"), 6);
		if (userDto.getUserId() == null || userDto.getUserId().equals("")) {
			userDto.setUserId(userId);
			responseDto.setMessage("User " + userId + " Created Successfully");
		}
		User user=importExportUtil.importUserDto(userDto);
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		responseDto.setMessage("User " + userDto.getUserId() + " Updated Successfully");
		responseDto.setStatus("OK");
		return responseDto;
	}

	@Override
	public UserDto getUserById(String id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		UserDto userDto= importExportUtil.exportUserDto(user);
		return userDto;
	}

	@Override
	public ResponseDto delete(UserDto userDto) {
		ResponseDto responseDto = new ResponseDto();
		String queryStr = "DELETE User WHERE userId=:userId";
		if (!ServiceUtil.isEmpty(userDto)) {
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setParameter("userId", userDto.getUserId());

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

}
