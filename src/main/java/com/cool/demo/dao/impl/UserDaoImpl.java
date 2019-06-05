package com.cool.demo.dao.impl;

import org.springframework.stereotype.Repository;
import com.core.dao.impl.SqlBaseDaoImpl;

import com.cool.demo.dao.UserDao;
import com.cool.demo.entity.User;

@Repository("userDao")
public class UserDaoImpl extends SqlBaseDaoImpl<User>implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

}
