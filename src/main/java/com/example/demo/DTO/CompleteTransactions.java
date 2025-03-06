package com.example.demo.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteTransactions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "account_reference")
    private String accountReference;

    @Column(name = "mpesa_code")
    private String mpesaCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "merchant_request_id")
    private String merchantRequestId;

    @Column(name = "result_code")
    private int resultCode;
}
