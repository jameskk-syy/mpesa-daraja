package com.example.demo.DTO;

import com.example.demo.Controllers.MpesaSTKTransactions;
import com.example.demo.Controllers.MpesaServiceInterface;
import com.example.demo.Repositorie.MpesaTransactionRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
@Slf4j
@Service
public class MpesaServiceImpl implements MpesaServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(MpesaServiceImpl.class);
    @Autowired
    private MpesaTransactionRepository mpesaTransactionRepository;

    @Value("${mpesa.app.key}")
    private String appKeY;

    @Value("${mpesa.app.secret}")
    private String appSecret;

    @Value("${mpesa.token.url}")
    private String stkAuthUrl;

    @Value("${mpesa.stk.url}")
    private String stkUrl;

    @Value("${mpesa.stk.transactionType}")
    private String transactionType;

    @Value("${mpesa.stk.password}")
    private String password;

    @Value("${mpesa.stk.shortCode}")
    private String shortCode;

    @Value("${mpesa.stk.callbackURL}")
    private String callBackUrl;

    @Value("${mpesa.b2c.url}")
    private  String b2cUrl;

    @Value("${mpesa.b2c.securityCredential}")
    private String securityCredential;
    @Value("${mpesa.b2c.initiatorPassword}")
    private String initiatorPassword;

    @Value("${mpesa.b2c.commandId}")
    private String b2cCommandId;

    @Value("${mpesa.b2c.queTimeOutURL}")
    private String queTimeOutURL;

    @Value("${mpesa.b2c.callBackURL}")
    private String b2cResultUrl;

    @Value("${mpesa.b2c.shortCode}")
    private  String b2cShortCode;

    @Value("${mpesa.b2c.initiatorName}")

    private String initiatorName;
    private String simulateEndpoint = "https://sandbox.safaricom.co.ke/mpesa/c2b/v1/simulate";
    @Value("${mpesa.base.url}")
    private String baseUrl;

    Gson gson = new Gson();
    private final RestTemplate restTemplate;

    public MpesaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Generate M-Pesa Access Token

    public String generateToken() {
        String appKeySecret = Credentials.basic(appKeY, appSecret);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(stkAuthUrl)
                .header("Authorization", appKeySecret)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse.getString("access_token");
            } else {
                throw new Exception("Failed to get access token. Response code: " + response.code());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public MpesaPaymentResponseDTO initiateSTKPush(String phoneNumber, Double amount, Long accountReference) {
//        return null;
//    }
public MpesaPaymentResponseDTO initiateSTKPushForMaany(@NotNull String phoneNumber, @NotNull Double amount ) {
    MpesaPaymentResponseDTO responseDTO = new MpesaPaymentResponseDTO();
    try {
        // Create HTTP client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/json");

        // Log initial information
        log.info(String.format("Short code: %s", shortCode));

        // Prepare request body
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        JSONObject requestBody = createRequestBody(amount, phoneNumber, timestamp );

        // Log the request body
        log.info(String.format("Initiate STK Push Request Body: %s", requestBody));

        // Convert to request body and initiate API request
        String requestJson = new JSONArray().put(requestBody).toString().replaceAll("[\\[\\]]", "");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        String token = generateToken();
        log.info(String.format("Token - %s", "Bearer " + token));
        Request request = new Request.Builder()
                .url(stkUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", token))
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Log and parse the response
        log.info("{ Mpesa Service } {Initiate STK Push} { Successful }");
        assert response.body() != null;
        responseDTO = gson.fromJson(response.body().string(), MpesaPaymentResponseDTO.class);

    } catch (Exception e) {
        // Log and populate error details
        log.error("{ Mpesa Service } {Initiate STK Push} { ERROR } - {}", e.getMessage());
    }

    return responseDTO;
}
    public MpesaPaymentResponseDTO initiateSTKPush(@NotNull String phoneNumber, @NotNull Double amount, Long accountReference) {
        MpesaPaymentResponseDTO responseDTO = new MpesaPaymentResponseDTO();

        try {
            log.info("Initiating STK Push for phone: {}", phoneNumber);

            // Ensure shortCode and stkUrl are correctly set
            log.info("Short code: {}", shortCode);
            log.info("STK Push URL: {}", stkUrl);

            // Configure OkHttpClient with reasonable timeouts
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            MediaType mediaType = MediaType.parse("application/json");

            // Generate timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

            // Prepare request body correctly
            JSONObject requestBody = createRequestBody(amount, phoneNumber, timestamp);
            String requestJson = requestBody.toString();

            // Log the request body for debugging
            log.info("Initiate STK Push Request Body: {}", requestJson);

            // Generate authentication token
            String token = generateToken();
            log.info("Generated Token: Bearer {}", token);

            // Build the API request
            RequestBody body = RequestBody.create(mediaType, requestJson);
            Request request = new Request.Builder()
                    .url(stkUrl)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            // Execute request and handle response
            Response response = client.newCall(request).execute();
            String responseBody = response.body() != null ? response.body().string() : "No Response Body";
            log.info("STK Push API Response: {}", responseBody);

            // Parse the response into DTO
            responseDTO = gson.fromJson(responseBody, MpesaPaymentResponseDTO.class);

            // Validate the response before proceeding
            if (responseDTO == null || responseDTO.getResponseCode() == null) {
                log.error("Failed STK Push: Invalid response received.");
                return responseDTO;
            }

            // Create and populate MpesaSTKTransactions entity
            MpesaSTKTransactions transaction = new MpesaSTKTransactions();
            transaction.setResponseCode(responseDTO.getResponseCode());
            transaction.setMerchantRequestID(responseDTO.getMerchantRequestID());
            transaction.setCheckoutRequestID(responseDTO.getCheckoutRequestID());
            transaction.setCustomerMessage(responseDTO.getCustomerMessage());
            transaction.setResponseDescription(responseDTO.getResponseDescription());
            transaction.setPhoneNumber(phoneNumber);
            transaction.setAmount(amount);

            // Save transaction details
            try {
                mpesaTransactionRepository.save(transaction);
                log.info("STK Push transaction saved successfully.");

            } catch (Exception e) {
                log.error("Error saving MpesaSTKTransaction: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            log.error("STK Push ERROR: {}", e.getMessage(), e);
        }

        return responseDTO;
    }
    private JSONObject createRequestBody(Double amount, String phoneNumber, String timestamp) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("BusinessShortCode", shortCode);
        requestBody.put("Password", generatePassword(shortCode, password, timestamp));
        requestBody.put("Timestamp", timestamp);
        requestBody.put("Amount", amount);
        requestBody.put("TransactionType", transactionType);
        requestBody.put("PartyA", phoneNumber);
        requestBody.put("PartyB", shortCode);
        requestBody.put("PhoneNumber", phoneNumber);
        requestBody.put("CallBackURL", callBackUrl);
        requestBody.put("AccountReference", "KIMWI SACCO");
        requestBody.put("TransactionDesc", "KIMWI SACCO");
        return requestBody;
    }



    public String generatePassword(String shortCode,String passkey,String timeStamp)
    {
        String credentials = shortCode+passkey+timeStamp;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }




    public Void processCallback(Object object) {
        log.info("------>" + "James Maina");
        Gson gson = new Gson();
        log.info("STK Callback received at " + new Date());

        // Parse the JSON object
        JSONObject callbackJson = new JSONObject(gson.toJson(object));

        // Initialize variables
        String resultCode = "";
        String resultDesc = "";
        String mpesaCode = "";
        String merchantRequestId = "";
        Timestamp transactionDate = null;
        long phoneNumber = 0L;
        Double amount = null;



        try {
            // Navigate through the JSON structure
            if (callbackJson.has("Body")) {
                JSONObject body = callbackJson.getJSONObject("Body");

                if (body.has("stkCallback")) {
                    JSONObject stkCallback = body.getJSONObject("stkCallback");

                    // Extract core callback fields
                    resultCode = stkCallback.optString("ResultCode", "");
                    resultDesc = stkCallback.optString("ResultDesc", "");
                    merchantRequestId = stkCallback.optString("MerchantRequestID", "");

                    // Extract callback metadata
                    if (stkCallback.has("CallbackMetadata")) {
                        JSONObject callbackMetadata = stkCallback.getJSONObject("CallbackMetadata");

                        if (callbackMetadata.has("Item")) {
                            JSONArray items = callbackMetadata.getJSONArray("Item");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                String name = item.optString("Name", "");

                                switch (name) {
                                    case "Amount":
                                        amount = item.optDouble("Value", 0.0);
                                        break;
                                    case "MpesaReceiptNumber":
                                        mpesaCode = item.optString("Value", "");
                                        break;
                                    case "TransactionDate":
                                        long rawDate = item.optLong("Value", 0L);
                                        String dateString = String.valueOf(rawDate);
                                        if (dateString.length() == 14) { // Ensure date format is correct
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                            transactionDate = new Timestamp(dateFormat.parse(dateString).getTime());
                                        }
                                        break;
                                    case "PhoneNumber":
                                        phoneNumber = item.optLong("Value", 0L);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }
            }

            // Log extracted values for debugging
            log.info("Result Code: {}", resultCode);
            log.info("Result Description: {}", resultDesc);
            log.info("Merchant Request ID: {}", merchantRequestId);
            log.info("Amount: {}", amount);
            log.info("Transaction Date: {}", transactionDate);
            log.info("Mpesa Code: {}", mpesaCode);
            log.info("Phone Number: {}", phoneNumber);

            // Validate necessary fields before proceeding
            if (merchantRequestId.isEmpty() || resultCode.isEmpty()) {
                log.error("Invalid callback response: Missing essential fields.");
                return null;
            }

            else if(!mpesaCode.isEmpty()){

            }

            String finalResultCode = resultCode;
            Double finalAmount = amount;
            String finalMpesaCode = mpesaCode;
            String finalResultDesc = resultDesc;
            Timestamp finalTransactionDate = transactionDate;
            long finalPhoneNumber = phoneNumber;
            String finalMerchantRequestId = merchantRequestId;



        } catch (Exception e) {
            log.error("Error processing STK callback: ", e);
        }

        return null;
    }

    private String parseAccessToken(String responseBody) {
        return "access_token_placeholder";
    }
}
