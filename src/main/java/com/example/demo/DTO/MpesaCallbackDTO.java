package com.example.demo.DTO;


public class MpesaCallbackDTO {
    private String merchantRequestID;
    private String checkoutRequestID;
    private String resultCode;
    private String resultDescription;
    private String mpesaReceiptNumber;
    private String transactionDate;
    private String phoneNumber;

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

    public String getMerchantRequestID() {
        return merchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        this.merchantRequestID = merchantRequestID;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMpesaReceiptNumber() {
        return mpesaReceiptNumber;
    }

    public void setMpesaReceiptNumber(String mpesaReceiptNumber) {
        this.mpesaReceiptNumber = mpesaReceiptNumber;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MpesaCallbackDTO() {
    }

    public MpesaCallbackDTO(String merchantRequestID, String checkoutRequestID, String resultCode, String resultDescription, String transactionDate, String mpesaReceiptNumber, String phoneNumber) {
        this.merchantRequestID = merchantRequestID;
        this.checkoutRequestID = checkoutRequestID;
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
        this.transactionDate = transactionDate;
        this.mpesaReceiptNumber = mpesaReceiptNumber;
        this.phoneNumber = phoneNumber;
    }
}
