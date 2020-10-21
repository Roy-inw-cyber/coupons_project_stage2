package com.roy.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.roy.beans.Company;
import com.roy.dao.repositories.CompaniesRepository;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompaniesDAO {

	private CompaniesRepository companiesRepository;

	@Autowired
	public CompaniesDAO(CompaniesRepository companiesRepository) {
		this.companiesRepository = companiesRepository;
	}
	
	public boolean isCompanyExists(String email, String password) throws CouponSystemException{
		try {
			return companiesRepository.findByEmailAndPassword(email, password) != null;
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCompanyExists in CompaniesDAO");
		}
	}
	
	public int getCompanyIdByCredentials(String email, String password) throws CouponSystemException{
		try {
			Company company = companiesRepository.findByEmailAndPassword(email, password);
			return company != null ? company.getId() : 0;
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getCompanyIdByCredentials in CompaniesDAO");
		}
	}

	public void addCompany(Company company) throws CouponSystemException{
		try {
			companiesRepository.save(company);
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method addCompany in CompaniesDAO");
		}
	}
	
	public void updateCompany(Company company) throws CouponSystemException{
		try {
			addCompany(company);
		} catch (CouponSystemException exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method updateCompany in CompaniesDAO");
		}
	}
	
	public void deleteCompany(int companyID) throws CouponSystemException{
		try {

			companiesRepository.deleteById(companyID);
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method deleteCompany in CompaniesDAO");
		}
	}
	
	public ArrayList<Company> getAllCompanies() throws CouponSystemException{
		try {
			return (ArrayList<Company>) companiesRepository.findAll();
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getAllCompanies in CompaniesDAO");
		}
	}

	public Company getOneCompany(int companyID) throws CouponSystemException{
		try {
			Optional<Company> company = companiesRepository.findById(companyID);
			return company.isPresent() ? company.get() : null;
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getOneCompany in CompaniesDAO");
		}
	}

	public boolean isCompanyNameExists(String name) throws CouponSystemException{
		try {
			return companiesRepository.findByName(name) != null;
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCompanyNameExists in CompaniesDAO");
		}
	}
	
	public boolean isCompanyEmailExists(String email) throws CouponSystemException{
		try {
			return companiesRepository.findByEmail(email) != null;
		} catch (Exception exception) {
			throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCompanyEmailExists in CompaniesDAO");
		}
	}
}
