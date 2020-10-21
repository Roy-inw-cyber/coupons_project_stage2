package com.roy.dao.repositories;

import com.roy.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select id from categories where name=:name", nativeQuery = true)
    Integer findIdByName(@Param("name") String name);
}
