package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.model.User;
import com.incture.zp.ereturns.repositories.UserRepository;

@Repository()
public class UserRepositoryImpl implements UserRepository {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public User getUserById(String id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

}
