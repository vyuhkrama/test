package com.test.web.dao;

import com.test.web.model.*;

public interface LoginDAOInterface {

	public boolean checkLogin(String userName, String userPassword);
}
