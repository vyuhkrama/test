package com.test.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.web.dao.LoginDAOInterface;

@Service("loginServiceInterface")
public class LoginServiceImpl implements LoginServiceInterface {

	@Autowired
	private LoginDAOInterface loginDAOInterface;
	 
	public void setLoginDAO(LoginDAOInterface loginDAO) {
		this.loginDAOInterface = loginDAO;
	}

	@Override
	public boolean checkLogin(String userName, String userPassword) {
		// TODO Auto-generated method stub
		return loginDAOInterface.checkLogin(userName, userPassword);
	}

	
}
