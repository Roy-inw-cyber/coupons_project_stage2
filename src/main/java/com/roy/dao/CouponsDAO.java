package com.roy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.roy.beans.Coupon;
import com.roy.dao.repositories.CategoriesRepository;
import com.roy.dao.repositories.CouponsRepository;
import com.roy.enums.Category;
import com.roy.enums.ErrorType;
import com.roy.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponsDAO {

    private CouponsRepository couponsRepository;
    private CategoriesRepository categoriesRepository;

    @Autowired
    public CouponsDAO(CouponsRepository couponsRepository,
                      CategoriesRepository categoriesRepository) {
        this.couponsRepository = couponsRepository;
        this.categoriesRepository = categoriesRepository;
    }
    
    public boolean isCouponExists(int couponID) throws CouponSystemException {
        try {
            return couponsRepository.findById(couponID) != null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method isCouponExists in CouponsDAO");
        }
    }

    public boolean isAlreadyPurchasedCoupon(int couponID, int customerID) throws CouponSystemException {
        try {
            return couponsRepository.findCustomerPurchase(customerID, couponID) != null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method isAlreadyPurchasedCoupon in CouponsDAO");
        }
    }

    public void addCoupon(Coupon coupon) throws CouponSystemException {
        try {
            couponsRepository.save(coupon);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method addCoupon in CouponsDAO");
        }
    }
    
    public void updateCoupon(Coupon coupon) throws CouponSystemException {
        try {
            addCoupon(coupon);
        } catch (CouponSystemException exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method updateCoupon in CouponsDAO");
        }
    }

    public void decreaseCouponAmount(int couponID) throws CouponSystemException {
        try {
            couponsRepository.decreaseCouponAmount(couponID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method decreaseCouponAmount in CouponsDAO");
        }
    }

    public void deleteCoupon(int couponID) throws CouponSystemException {
        try {
            couponsRepository.deleteById(couponID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteCoupon in CouponsDAO");
        }
    }
    
    public ArrayList<Coupon> getAllCoupons() throws CouponSystemException {
        try {
            return (ArrayList<Coupon>) couponsRepository.findAll();
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCoupons in CouponsDAO");
        }
    }
    
    public Coupon getOneCoupon(int couponID) throws CouponSystemException {
        try {
            Optional<Coupon> coupon = couponsRepository.findById(couponID);
            return coupon.isPresent() ? coupon.get() : null;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getOneCoupon in CouponsDAO");
        }
    }

    private int getCategoryIdByName(Category category) throws CouponSystemException {
        try {
            return categoriesRepository.findIdByName(category.name().toUpperCase());
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getCategoryIdByName in CouponsDAO");
        }
    }
    
    public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
        try {
            couponsRepository.saveCustomerPurchase(customerID, couponID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method addCouponPurchase in CouponsDAO");
        }
    }
    
    public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException {
        try {
            couponsRepository.deleteCustomerPurchase(customerID, couponID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteCouponPurchase in CouponsDAO");
        }
    }
    
    public ArrayList<Coupon> getAllCouponsByCustomerID(int customerID) throws CouponSystemException {
        try {
            return couponsRepository.findAllByCustomerId(customerID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCustomerID in CouponsDAO");
        }
    }

    public ArrayList<Coupon> getAllCouponsByCustomerIDAndCategoryID(int customerID, Category category)
            throws CouponSystemException {
        try {
            return couponsRepository.findAllByCustomerIdAndCategory(customerID, category.name().toUpperCase());
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCustomerIDAndCategoryID in CouponsDAO");
        }
    }
    
    public ArrayList<Coupon> getAllCouponsByCustomerIDAndMaxPrice(int customerID, double maxPrice)
            throws CouponSystemException {
        try {
            return couponsRepository.findAllByCustomerIdAndMaxPrice(customerID, maxPrice);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCustomerIDAndMaxPrice in CouponsDAO");
        }
    }

    public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID) throws CouponSystemException {
        try {
//           String sqlStatement = "select coupons.*, categories.name category_name "
//                    + "from coupons left join categories on coupons.category_id = categories.id where company_id = ?";
            return couponsRepository.findAllByCompanyID(companyID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCompanyID in CouponsDAO");
        }
    }
    
    public ArrayList<Coupon> getAllCouponsByCompanyIDAndCategoryID(int companyID, Category category)
            throws CouponSystemException {
        try {
            return couponsRepository.findAllByCompanyIDAndCategory(companyID, category.name().toUpperCase());
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCompanyIDAndCategoryID in CouponsDAO");
        }
    }
    
    public ArrayList<Coupon> getAllCouponsByCompanyIDAndMaxPrice(int companyID, double maxPrice)
            throws CouponSystemException {
        try {
            return couponsRepository.findAllByCompanyIDAndPrice(companyID, maxPrice);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method getAllCouponsByCompanyIDAndMaxPrice in CouponsDAO");
        }
    }

    public void deleteCouponsByCompanyId(int companyID) throws CouponSystemException {
        try {
            couponsRepository.deleteAllByCompanyID(companyID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteCouponByCompanyId in CouponsDAO");
        }
    }

    public void deleteExpiredCoupons() throws CouponSystemException {
        try {
            //get a list of the expired coupons comma delimited
            deleteExpiredPurchasedCoupons();
            couponsRepository.deleteAllByEndDateBefore(new Date());
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteExpiredCoupons in CouponsDAO");
        }
    }
    
    public void deletePurchasedCouponsByCompanyID(int companyID) throws CouponSystemException {
        try {
            couponsRepository.deleteCustomerPurchaseByCompanyId(companyID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteExpiredPurchasedCoupons in CouponsDAO");
        }
    }

    private void deleteExpiredPurchasedCoupons() throws CouponSystemException {
        try {
            List<Coupon> expiredCoupons = couponsRepository.findAllByEndDateBefore(new Date());
            if (expiredCoupons != null) {
                expiredCoupons.forEach((coupon -> couponsRepository.deleteCustomerPurchaseByCouponId(coupon.getId())));
            }
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deleteExpiredPurchasedCoupons in CouponsDAO");
        }
    }

    public void deletePurchasedCouponsByCustomerID(int customerID) throws CouponSystemException {
        try {
           couponsRepository.deleteCustomerPurchaseByCustomerId(customerID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deletePurchasedCouponsByCustomerID in CouponsDAO");
        }
    }
    
    public boolean isCouponTitleExistsByCompanyID(String couponTitle, int companyID) throws CouponSystemException {
      try {
          ArrayList<Coupon> list = couponsRepository.findAllByCompanyIDAndTitle(companyID, couponTitle);
            return list != null && list.size()>0;
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_GET_ERROR,
                    "error running method isCouponNameExistsByCompanyID in CouponsDAO");
        }
    }
    
    public void deletePurchasedCouponsByCouponID(int couponID) throws CouponSystemException {
        try {
          couponsRepository.deleteCustomerPurchaseByCouponId(couponID);
        } catch (Exception exception) {
            throw new CouponSystemException(exception, ErrorType.DB_SET_ERROR,
                    "error running method deletePurchasedCouponsByCouponID in CouponsDAO");
        }
    }

}
