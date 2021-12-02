package com.springmvc.dao.impl;



import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springmvc.dao.AccountDAO;
import com.springmvc.entity.Accounts;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Accounts getAccountByUsername(String userName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT ACC FROM Accounts ACC WHERE ACC.userName = :USERNAME";
		Query<Accounts> query = session.createQuery(hql);
		query.setParameter("USERNAME", userName);
		Accounts account = (Accounts) query.uniqueResult();
		return account;
	}

}
