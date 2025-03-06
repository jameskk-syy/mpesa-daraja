package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MpesaPaymentRequestDTO {

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;
    @JsonProperty(value = "amount")
    private Double amounts;
    @JsonProperty
    private Long accountReference;


//    @JsonProperty
//    private String transactionDescription;


    public MpesaPaymentRequestDTO(String phoneNumber, Double amounts, Long accountReference) {
        this.phoneNumber = phoneNumber;
        this.amounts = amounts;
        this.accountReference = accountReference;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getAmounts() {
        return amounts;
    }

    public void setAmounts(Double amounts) {
        this.amounts = amounts;
    }

    public Long getAccountReference() {
        return accountReference;
    }

    public void setAccountReference(Long accountReference) {
        this.accountReference = accountReference;
    }

    public MpesaPaymentRequestDTO() {
    }
}
