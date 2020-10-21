package com.roy;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.roy.beans.Company;
import com.roy.beans.Coupon;
import com.roy.beans.Customer;
import com.roy.enums.Category;
import com.roy.enums.ClientType;
import com.roy.facade.AdminFacade;
import com.roy.facade.CompanyFacade;
import com.roy.facade.CustomerFacade;
import com.roy.handlers.LoginManager;
import com.roy.jobs.CouponExpirationDailyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test {

    private LoginManager loginManager;
    private CouponExpirationDailyJob couponExpirationDailyJob;
    private Company company;
    private Company updatedCompany;
    private Customer customer;
    private Customer updatedCustomer;
    private Coupon coupon;
    private Coupon updatedCoupon;

    @Autowired
    public Test(LoginManager loginManager,
                CouponExpirationDailyJob couponExpirationDailyJob) {
        try {
            this.loginManager = loginManager;
            this.couponExpirationDailyJob = couponExpirationDailyJob;
            init();
        } catch (Exception e) {
            System.out.println("init params failed inside ctor");
        }
    }

    public void runTests() {
        try {
            startJob();
//            testAdmin();
//            testCompany();
//            testCustomer();
//            endJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startJob() {
        Thread job = new Thread(couponExpirationDailyJob);
        job.start();
    }

    public void endJob() {
        couponExpirationDailyJob.stop();
    }

    public void testCompany() throws Exception {
        CompanyFacade companyFacade = (CompanyFacade) loginManager.login("elite@manage.com", "eli122",
                ClientType.COMPANY);
        companyFacade.addCoupon(coupon);
        companyFacade.updateCoupon(updatedCoupon);
        companyFacade.deleteCoupon(updatedCoupon);
        System.out.println(companyFacade.getCompanyCoupons());
        System.out.println(companyFacade.getCompanyCoupons(Category.ELECTRICITY));
        System.out.println(companyFacade.getCompanyCoupons(1000));
        System.out.println(companyFacade.getCompanyDetails());
    }

    public void testCustomer() throws Exception {
        CustomerFacade customerFacade = (CustomerFacade) loginManager.login("binya1950@gmail.com", "17668fkdk",
                ClientType.CUSTOMER);
        customerFacade.purchaseCoupon(updatedCoupon);
        System.out.println(customerFacade.getCustomerCoupons());
        System.out.println(customerFacade.getCustomerCoupons(Category.ELECTRICITY));
        System.out.println(customerFacade.getCustomerCoupons(1605));
        System.out.println(customerFacade.getCustomerDetails());
    }

    public void testAdmin() throws Exception {
        AdminFacade adminFacade = (AdminFacade) loginManager.login("admin@admin.com", "admin",
                ClientType.ADMINISTRATOR);
        adminFacade.addCompany(company);
        adminFacade.updateCompany(updatedCompany);
        adminFacade.deleteCompany(11);
        System.out.println(adminFacade.getAllCompanies());
        System.out.println(adminFacade.getOneCompany(1));
        adminFacade.addCustomer(customer);
        adminFacade.updateCustomer(updatedCustomer);
        adminFacade.deleteCustomer(13);
        System.out.println(adminFacade.getAllCustomers());
        System.out.println(adminFacade.getOneCustomer(10));
    }

    private void init() throws Exception {
        company = new Company("Takamine", "takamine@guitars.com", "guitars");
        updatedCompany = new Company(7, "Takamine", "takamine@guitars.com", "gui7462mj11111");
        customer = new Customer("guy", "goldin", "guygoll@gmail.com", "hghgjk");
        updatedCustomer = new Customer(12, "guy", "gold", "guygo@gmail.com", "hello");
        coupon = new Coupon(7, Category.ELECTRICITY, "test22",
                "best collections of electic guitars with prices that you won't find",
                new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-27").getTime()),
                new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01").getTime()), 20, 1600,
                "https://i.ebayimg.com/images/g/eewAAOSwbO5e6Ya9/s-l640.jpg");
        updatedCoupon = new Coupon(5, 4, Category.ELECTRICITY, "off electric guitars",
                "best collections of electic guitars with prices that you won't find",
                new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-27").getTime()),
                new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-01").getTime()), 20, 1600,
                "https://i.ebayimg.com/images/g/eewAAOSwbO5e6Ya9/s-l640.jpg");
    }
}
