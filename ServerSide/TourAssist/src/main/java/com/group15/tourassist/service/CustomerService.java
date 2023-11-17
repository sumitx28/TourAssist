package com.group15.tourassist.service;

import com.group15.tourassist.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    @Override
    public Customer getCustomerByAppUserId(Long appUserId) {
        return null;
    }
}
