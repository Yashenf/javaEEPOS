package com.yashen.eestudyapp.service.serviceFactory;

import com.yashen.eestudyapp.service.SuperService;
import com.yashen.eestudyapp.service.impl.CustomerServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory(){}

    public static ServiceFactory getServiceFactory(){
        return serviceFactory == null? (serviceFactory=new ServiceFactory()):serviceFactory;
    }
    public <T extends SuperService>T getService(ServiceTypes serviceType){
        switch (serviceType){
            case CUSTOMER:
                return (T) new CustomerServiceImpl();
            default:
                return null;
        }
    }
}
