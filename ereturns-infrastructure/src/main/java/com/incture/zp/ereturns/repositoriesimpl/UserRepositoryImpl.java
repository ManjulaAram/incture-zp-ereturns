package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.dto.ResponseDto;
import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;
import com.incture.zp.ereturns.utils.GetReferenceData;

@Repository()
public class UserRepositoryImpl implements UserRepository {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired 
	private GetReferenceData getReferenceData;
	
	@Override
	public ResponseDto addUser(User user) {
		ResponseDto responseDto = new ResponseDto();
		String userId = getReferenceData.getNextSeqNumber(getReferenceData.execute("U"), 6, sessionFactory);
		if(user.getUserId() == null || user.getUserId().equals("")) {
			user.setUserId(userId);
			responseDto.setMessage("User "+ userId +" Created Successfully");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		responseDto.setMessage("User "+user.getUserId()+" Updated Successfully");
		responseDto.setStatus("OK");
		return responseDto;
	}

	@Override
	public User getUserById(String id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public ResponseDto updateUser(User user) {
		return null;
	}

	@Override
	public ResponseDto deleteUser(String id) {
		return null;
	}

}
