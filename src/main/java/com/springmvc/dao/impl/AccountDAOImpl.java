package com.springmvc.dao.impl;



import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.springmvc.dao.AccountDAO;
import com.springmvc.entity.Accounts;
import com.springmvc.models.AccountInfo;
import com.springmvc.models.PaginationResult;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Accounts getAccountByUsername(String userName) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT ACC FROM Accounts ACC WHERE ACC.userName = :USERNAME";
		Query<Accounts> query = session.createQuery(hql);
		query.setParameter("USERNAME", userName);
		Accounts account = (Accounts) query.uniqueResult();
		return account;
	}

	@Override
	public PaginationResult<AccountInfo> getAllAccountInfos(int page, int maxResult, int maxNavigationPage) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT NEW " + AccountInfo.class.getName()
				+ " (ACC.userName, ACC.password, ACC.enabled, ACC.userRole) FROM Accounts ACC";
		Query<AccountInfo> query = session.createQuery(hql);
		return new PaginationResult<AccountInfo>(query, page, maxResult, maxNavigationPage);
	}

	@Override
	public void saveAccountInfo(AccountInfo accountInfo) {

		Session session = sessionFactory.getCurrentSession();
		String username = accountInfo.getUserName();
		Accounts account = null;
		boolean isNew = false;

		if (username != null) {
			account = getAccountByUsername(username);
		}

		if (account == null) {
			isNew = true;
			account = new Accounts();
		}

		account.setUserName(username);
		String encodedPassword = bCryptPasswordEncoder.encode(accountInfo.getPassword());
		account.setPassword(encodedPassword);
		account.setUserRole(accountInfo.getUserRole());
		account.setEnabled(accountInfo.isEnabled());

		if (isNew) {
			session.persist(account);
		}
		session.flush();

	}

	@Override
	public boolean deleteAccountByUsername(String userName) {
		// TODO Auto-generated method stub
				try {
					Session session = sessionFactory.getCurrentSession();
					String hql = "DELETE FROM Accounts ACC WHERE ACC.userName = :USERNAME1";
					Query query = session.createQuery(hql);
					query.setParameter("USERNAME1", userName);
					query.executeUpdate();
					return true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				return false;
	}


}
