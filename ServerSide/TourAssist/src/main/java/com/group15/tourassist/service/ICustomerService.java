package com.group15.tourassist.service;

import com.group15.tourassist.entity.Customer;

public interface ICustomerService {

    Customer getCustomerByAppUserId(Long appUserId);
}
