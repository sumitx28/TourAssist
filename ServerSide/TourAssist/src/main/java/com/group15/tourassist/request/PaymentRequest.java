package com.group15.tourassist.request;

import com.group15.tourassist.core.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentRequest {

    private String transactionId;

    private Long bookingId;

    private String paymentType;

    private TransactionStatus transactionStatus;

    private Double price;
}
