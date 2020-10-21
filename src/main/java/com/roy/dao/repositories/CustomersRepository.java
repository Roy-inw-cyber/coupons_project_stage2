package com.roy.dao.repositories;

import com.roy.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmailAndPassword(String email, String password);
    Customer findByEmail(String email);
}
