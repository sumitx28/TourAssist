package com.group15.tourassist.entity;

import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.request.PaymentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "payment_transaction")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "price")
    private Double price;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    /**
     * @param request create payment request
     * @param booking corresponding booking object
     * @return PaymentTransaction entity
     */
    public static PaymentTransaction getPaymentByRequest(PaymentRequest request, Booking booking) {
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setBooking(booking);
        paymentTransaction.setPaymentType(request.getPaymentType());
        paymentTransaction.setTransactionId(request.getTransactionId());
        paymentTransaction.setPrice(request.getPrice());
        paymentTransaction.setTransactionDate(Instant.now());
        paymentTransaction.setTransactionStatus(request.getTransactionStatus());

        return paymentTransaction;
    }
}
