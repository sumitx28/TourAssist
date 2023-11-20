package com.group15.tourassist.service;

import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    ICustomerRepository customerRepository;

    /**
     * @param appUserId appUserId to fetch customer details
     * @return Customer details
     */
    @Override
    public Customer getCustomerByAppUserId(Long appUserId) {
        Optional<Customer> customerOptional = customerRepository.getCustomerByAppUserId(appUserId);
        return customerOptional.orElse(null);
    }
}
