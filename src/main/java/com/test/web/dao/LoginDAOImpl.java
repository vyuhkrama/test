package com.test.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("loginDAOInterface")
public class LoginDAOImpl implements LoginDAOInterface {

	 @Resource(name="sessionFactory")
     protected SessionFactory sessionFactory;

     public void setSessionFactory(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
     }
    
     protected Session getSession(){
            return sessionFactory.openSession();
     }
	
	
	@Override
	public boolean checkLogin(String userName, String userPassword) {
		// TODO Auto-generated method stub
		System.out.println("In Check login");
		Session session = sessionFactory.openSession();
		boolean userFound = false;
		String SQL_QUERY ="from User as o where o.name=? and o.password=?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0,userName);
		query.setParameter(1,userPassword);
		
		List list = query.list();

		if ((list != null) && (list.size() > 0)) {
			userFound= true;
		}

		session.close();
		return userFound;              
	}

}
