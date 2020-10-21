package com.roy.facade;

import com.roy.dao.CompaniesDAO;
import com.roy.dao.CouponsDAO;
import com.roy.dao.CustomersDAO;
import com.roy.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientFacade {

	@Autowired
	protected CustomersDAO customersDao;

	@Autowired
	protected CouponsDAO couponsDao;

	@Autowired
	protected CompaniesDAO companiesDao;
	
	public abstract boolean login(String email, String password) throws CouponSystemException;

}
