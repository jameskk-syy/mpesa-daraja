package com.example.demo.Controllers;
import com.example.demo.DTO.MpesaPaymentRequestDTO;
import com.example.demo.DTO.MpesaPaymentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/")
public class MPesaController {

    private static final Logger log = LoggerFactory.getLogger(MPesaController.class);
    private final MpesaServiceInterface mpesaService;

    public MPesaController(MpesaServiceInterface mpesaService) {
        this.mpesaService = mpesaService;
    }
    @GetMapping("/")
    public  String printName(){
        return "hello  world";
    }

    @PostMapping("/initiate-payment")
    public MpesaPaymentResponseDTO initiatePayment(@RequestBody MpesaPaymentRequestDTO body) {
        log.info("Received request: Amount = {}, Phone Number = {}", body.getAmounts(), body.getPhoneNumber());
        return mpesaService.initiateSTKPush(body.getPhoneNumber(), body.getAmounts(), body.getAccountReference());
    }

    @PostMapping(path = "/stk-callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public void stkPushCallback(@RequestBody Object object) {
        log.info("------>" + "James Maina");
        log.info("STK PUSH Callback Response: {}", object);
        mpesaService.processCallback(object);
    }

    @GetMapping("/generate-token")
    public ResponseEntity<String> generateToken() {
        String response = mpesaService.generateToken();
        return ResponseEntity.ok(response);
    }
}
