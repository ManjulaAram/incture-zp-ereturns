package com.incture.zp.ereturns.repositories;

import com.incture.zp.ereturns.model.User;

public interface UserRepository {

	public void addUser(User user);
	
	public User getUserById(String id);
	
}
