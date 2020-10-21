package com.roy.dao.repositories;

import com.roy.beans.Coupon;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface CouponsRepository extends JpaRepository<Coupon, Integer> {

    @Query(value = "update coupons set amount=amount-1 where id=:id",
            nativeQuery = true)
    @Modifying
    @Transactional
    void decreaseCouponAmount(@Param("id") int id);

    @Query(value = "insert into customers_vs_coupons values (:customerId,:couponId)",
            nativeQuery = true)
    @Modifying
    @Transactional
    void saveCustomerPurchase(@Param("customerId") int customerId,
                              @Param("couponId") int couponId);

    @Query(value = "delete from customers_vs_coupons where customer_id=:customerId and coupon_id=:couponId",
            nativeQuery = true)
    @Modifying
    @Transactional
    void deleteCustomerPurchase(@Param("customerId") int customerId,
                              @Param("couponId") int couponId);

    @Query(value = "delete from customers_vs_coupons \n" +
            "where coupon_id in(\n" +
            "   select distinct coupon_id from coupons where company_id=:companyId \n" +
            ")",
            nativeQuery = true)
    @Modifying
    @Transactional
    void deleteCustomerPurchaseByCompanyId(@Param("companyId") int companyId);

    @Query(value = "delete from customers_vs_coupons where coupon_id=:couponId",
            nativeQuery = true)
    @Modifying
    @Transactional
    void deleteCustomerPurchaseByCouponId(@Param("couponId") int couponId);

    @Query(value = "delete from customers_vs_coupons where customer_id=:customerId",
            nativeQuery = true)
    @Modifying
    @Transactional
    void deleteCustomerPurchaseByCustomerId(@Param("customerId") int customerId);


    @Query(value = "select customer_id from customers_vs_coupons where customer_id=:customerId and coupon_id=:couponId",
            nativeQuery = true)
    Integer findCustomerPurchase(@Param("customerId") int customerId,
                              @Param("couponId") int couponId);

    @Query(value = "select coupons.*, categories.name category "
                + "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
                + "left join categories on coupons.category_id = categories.id "
                + "where customers_vs_coupons.customer_id = :customerId",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCustomerId(@Param("customerId") int customerId);

    @Query(value = "select coupons.*, categories.name category "
            + "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
            + "left join categories on coupons.category_id = categories.id "
            + "where customers_vs_coupons.customer_id = :customerId and categories.name = :categoryName",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCustomerIdAndCategory(@Param("customerId") int customerId,
                                                  @Param("categoryName") String categoryName);

    @Query(value = "select coupons.*, categories.name category "
            + "from customers_vs_coupons left join coupons on coupons.id = customers_vs_coupons.coupon_id "
            + "left join categories on coupons.category_id = categories.id "
            + "where customers_vs_coupons.customer_id = :customerId and coupons.price < :maxPrice",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCustomerIdAndMaxPrice(@Param("customerId") int customerId,
                                                     @Param("maxPrice") double maxPrice);
    @Query(value = "select coupons.*, categories.name category " +
            "from coupons left join categories on coupons.category_id = categories.id " +
            "where company_id = :companyId",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCompanyID(@Param("companyId") int companyId);

    @Query(value = "select coupons.*, categories.name category " +
            "from coupons left join categories on coupons.category_id = categories.id " +
            "where company_id = :companyId and title = :title",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCompanyIDAndTitle(@Param("companyId") int companyId,
                                                  @Param("title") String title);

    @Query(value = "select coupons.*, categories.name category " +
            "from coupons left join categories on coupons.category_id = categories.id " +
            "where company_id = :companyId and categories.name = :categoryName",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCompanyIDAndCategory(@Param("companyId") int companyId,
                                                  @Param("categoryName") String categoryName);

    @Query(value = "select coupons.*, categories.name category " +
            "from coupons left join categories on coupons.category_id = categories.id " +
            "where company_id = :companyId and coupons.price < :maxPrice",
            nativeQuery = true)
    ArrayList<Coupon> findAllByCompanyIDAndPrice(@Param("companyId") int companyId,
                                                  @Param("maxPrice") double maxPrice);

    @Modifying @Transactional
    void deleteAllByCompanyID(int companyID);

    @Modifying @Transactional
    void deleteAllByEndDateBefore(Date endDate);

    List<Coupon> findAllByEndDateBefore(Date endDate);

}
