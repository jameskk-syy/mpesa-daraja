package com.example.demo.DTO;

public class MpesaPaymentResponseDTO {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResponseCode;
    private String ResponseDescription;
    private String CustomerMessage;

    public MpesaPaymentResponseDTO(String merchantRequestID, String checkoutRequestID, String responseDescription, String responseCode, String customerMessage) {
        MerchantRequestID = merchantRequestID;
        CheckoutRequestID = checkoutRequestID;
        ResponseDescription = responseDescription;
        ResponseCode = responseCode;
        CustomerMessage = customerMessage;
    }

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }

    public String getCustomerMessage() {
        return CustomerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        CustomerMessage = customerMessage;
    }

    public MpesaPaymentResponseDTO() {
    }
}

