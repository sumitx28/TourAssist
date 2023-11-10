package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    /**
     * @param appUserId
     * @param mobile
     * @return the number of database records updated, 1 in this case
     */
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.mobile = :mobile WHERE c.appUser.id = :appUserId")
    int updateCustomerMobile(@Param("appUserId") Long appUserId, @Param("mobile") String mobile);


}

