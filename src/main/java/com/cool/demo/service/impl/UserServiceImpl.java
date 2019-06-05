package com.cool.demo.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.core.service.impl.SqlBaseServiceImpl;
import com.cool.demo.dao.UserDao;
import com.cool.demo.entity.User;
import com.cool.demo.service.UserService;

@Service("userService")
public class UserServiceImpl extends SqlBaseServiceImpl<User> implements UserService {

	@SuppressWarnings("unused")
	private UserDao userDao;
	
	public UserServiceImpl(@Qualifier("userDao")UserDao userDao) {
		super(userDao);
		this.userDao=userDao;
	}

}
