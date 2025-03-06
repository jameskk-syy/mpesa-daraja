package com.example.demo.Controllers;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(of = {"id"})
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mpesa_stk_transaction", uniqueConstraints = {
        @UniqueConstraint(name = "mpesa_transaction_id", columnNames = {"id"}),
        @UniqueConstraint(name = "mpesa_transaction_merchant_request_id", columnNames = {"merchant_request_id"})
})
public class  MpesaSTKTransactions implements Serializable {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "merchant_request_id")
    private String merchantRequestID;

    @Column(name = "checkout_request_id")
    private String checkoutRequestID;

    @Column(name = "customer_message")
    private String customerMessage;

    @Column(name = "response_description")
    private String responseDescription;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "amount")
    private Double amount;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "studentId")
//    private Student accountReference;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    @Column(name = "create_date", nullable = false)
    private Timestamp createdDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMerchantRequestID() {
        return merchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        this.merchantRequestID = merchantRequestID;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
