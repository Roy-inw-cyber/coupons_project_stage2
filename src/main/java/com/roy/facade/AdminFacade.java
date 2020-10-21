package com.roy.facade;

import java.util.ArrayList;
import com.roy.beans.Company;
import com.roy.beans.Customer;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.Override;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdminFacade extends ClientFacade {

	private static final String ADMIN_EMAIL = "admin@admin.com";
	private static final String ADMIN_PASSWORD = "admin";

	// adding a new company, if the chosen name and email does not exist in db
	public void addCompany(Company company) throws CouponSystemException {
		if (super.companiesDao.isCompanyNameExists(company.getName())) {
			throw new CouponSystemException(ErrorType.NAME_ALREADY_EXISTS,
					"error while running addCompany in AdminFacade, chosen name is already exists");
		}
		if (super.companiesDao.isCompanyEmailExists(company.getEmail())) {
			throw new CouponSystemException(ErrorType.EMAIL_ALREADY_EXISTS,
					"error while running addCompany in AdminFacade, chosen email is already exists");
		}
		super.companiesDao.addCompany(company);
	}

	// updating an existing company, if the updated values are not id or name
	public void updateCompany(Company company) throws CouponSystemException {
		Company currentCompanyInDb = super.companiesDao.getOneCompany(company.getId());
		if (currentCompanyInDb != null) {
			if (company.getId() != currentCompanyInDb.getId()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCompany in AdminFacade, field 'id' is not allowed to be updated");
			}
			if (!company.getName().equals(currentCompanyInDb.getName())) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCompany in AdminFacade, field 'name' is not allowed to be updated");
			}
			super.companiesDao.updateCompany(company);
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running updateCompany in AdminFacade, company id does not exist in the db");
		}
	}

	// deleting a company and all it's dependencies
	public void deleteCompany(int companyID) throws CouponSystemException {
		boolean isCompanyExists = super.companiesDao.getOneCompany(companyID) != null;
		if (isCompanyExists) {
			super.couponsDao.deletePurchasedCouponsByCompanyID(companyID);
			super.couponsDao.deleteCouponsByCompanyId(companyID);
			super.companiesDao.deleteCompany(companyID);
		}
	}

	public ArrayList<Company> getAllCompanies() throws CouponSystemException {
		return super.companiesDao.getAllCompanies();
	}

	public Company getOneCompany(int companyID) throws CouponSystemException {
		return super.companiesDao.getOneCompany(companyID);
	}

	// adding a new customer, if the chosen email does not exist in db
	public void addCustomer(Customer customer) throws CouponSystemException {
		if (super.customersDao.isCustomerEmailExists(customer.getEmail())) {
			throw new CouponSystemException(ErrorType.EMAIL_ALREADY_EXISTS,
					"error while running addCompany in addCustomer, chosen email is already exists");
		}
		super.customersDao.addCustomer(customer);
	}

	// updating an existing customer, if the updated values are not id
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer currentCustomerInDb = super.customersDao.getOneCustomer(customer.getId());
		if (currentCustomerInDb != null) {
			if (customer.getId() != currentCustomerInDb.getId()) {
				throw new CouponSystemException(ErrorType.INVALID_UPDATE,
						"error while running updateCustomer in AdminFacade, field 'id' is not allowed to be updated");
			}
			super.customersDao.updateCustomer(customer);
		} else {
			throw new CouponSystemException(ErrorType.INVALID_UPDATE,
					"error while running updateCustomer in AdminFacade, customer id does not exist in the db");
		}

	}

	// deleting a customer and all it's dependencies
	public void deleteCustomer(int customerID) throws CouponSystemException {
		super.couponsDao.deletePurchasedCouponsByCustomerID(customerID);
		super.customersDao.deleteCustomer(customerID);
	}

	public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
		return super.customersDao.getAllCustomers();
	}

	public Customer getOneCustomer(int customerID) throws CouponSystemException {
		return super.customersDao.getOneCustomer(customerID);
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
	}

}
