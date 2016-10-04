package com.tcs.dao;

import com.tcs.model.User;

public interface UserDaoInterface {
	
	public User findUserByUsername(String username);

}
