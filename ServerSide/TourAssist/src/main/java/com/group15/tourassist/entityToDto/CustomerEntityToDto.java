package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.CustomerDTO;
import com.group15.tourassist.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityToDto {
    public CustomerDTO customerEntityToDto(Customer customer){
        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setCustomerId(customer.getId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setMobile(customer.getMobile());
        customerDTO.setDateOfBirth(customer.getDateOfBirth());
        customerDTO.setCountry(customer.getCountry());

        return customerDTO;

    }
}
