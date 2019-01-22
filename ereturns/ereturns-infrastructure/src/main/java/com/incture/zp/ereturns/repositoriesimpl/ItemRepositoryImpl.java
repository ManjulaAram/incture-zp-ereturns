package com.incture.zp.ereturns.repositoriesimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incture.zp.ereturns.model.Item;
import com.incture.zp.ereturns.repositories.ItemRepository;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Item getItemById(String id) {
		return (Item) sessionFactory.getCurrentSession().get(Item.class, id);
	}

}
