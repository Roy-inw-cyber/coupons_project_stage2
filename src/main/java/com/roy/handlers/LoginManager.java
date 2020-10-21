package com.roy.handlers;

import com.roy.dao.CompaniesDAO;
import com.roy.dao.CouponsDAO;
import com.roy.dao.CustomersDAO;
import com.roy.enums.ClientType;
import com.roy.exception.CouponSystemException;
import com.roy.facade.AdminFacade;
import com.roy.facade.ClientFacade;
import com.roy.facade.CompanyFacade;
import com.roy.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class LoginManager {

    private ApplicationContext appContext;

    @Autowired
    private LoginManager(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
        switch (clientType) {
            case COMPANY:
                CompanyFacade companyFacade = appContext.getBean(CompanyFacade.class);
                if (companyFacade.login(email, password)) {
                    return companyFacade;
                }
                return null;
            case CUSTOMER:
                CustomerFacade customerFacade = appContext.getBean(CustomerFacade.class);
                if (customerFacade.login(email, password)) {
                    return customerFacade;
                }
                return null;
            case ADMINISTRATOR:
                AdminFacade adminFacade = appContext.getBean(AdminFacade.class);
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                return null;
        }
        return null;
    }
}
