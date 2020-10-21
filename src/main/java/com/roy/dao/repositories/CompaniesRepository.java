package com.roy.dao.repositories;

import com.roy.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Integer> {

    Company findByEmailAndPassword(String email, String password);
    Company findByName(String name);
    Company findByEmail(String email);
}
