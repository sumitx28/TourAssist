package com.group15.tourassist.repository;

import com.group15.tourassist.entity.PaymentTransaction;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentTransaction extends JpaRepository<PaymentTransaction, Long> {
}
