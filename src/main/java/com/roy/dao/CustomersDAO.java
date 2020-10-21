package com.roy.dao;

import com.roy.beans.Customer;
import com.roy.dao.repositories.CustomersRepository;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomersDAO {

    private CustomersRepository customersRepository;

    @Autowired
    public CustomersDAO(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public boolean isCustomerExists(String email, String password) throws CouponSystemException {
        try {
            return customersRepository.findByEmailAndPassword(email, password) != null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCustomerExists in CustomersDBDAO");
        }
    }

    public void addCustomer(Customer customer) throws CouponSystemException {
        try {
            customersRepository.save(customer);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method addCustomer in CustomersDBDAO");
        }
    }

    public void updateCustomer(Customer customer) throws CouponSystemException {
        try {
            addCustomer(customer);
        } catch (CouponSystemException exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method updateCustomer in CustomersDBDAO");
        }
    }

    public void deleteCustomer(int customerID) throws CouponSystemException {
        try {
            customersRepository.deleteById(customerID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR, "error running method deleteCustomer in CustomersDBDAO");
        }
    }

    public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
        try {
            return (ArrayList<Customer>) customersRepository.findAll();
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getAllCustomers in CustomersDBDAO");
        }
    }

    public Customer getOneCustomer(int customerID) throws CouponSystemException {
        try {
            Optional<Customer> customer = customersRepository.findById(customerID);
            return customer.isPresent() ? customer.get() : null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getOneCustomer in CustomersDBDAO");
        }
    }

    public boolean isCustomerEmailExists(String email) throws CouponSystemException {
        try {
            return customersRepository.findByEmail(email) != null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method isCustomerEmailExists in CustomersDBDAO");
        }
    }

    public int getCustomerIdByCredentials(String email, String password) throws CouponSystemException {
        try {
            Customer customer = customersRepository.findByEmailAndPassword(email, password);
            return customer != null ? customer.getId() : 0;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR, "error running method getCustomerIdByCredentials in CustomersDBDAO");
        }
    }

}
