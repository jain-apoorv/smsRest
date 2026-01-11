package com.vardh.smsRest.sms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/mock-sms-vendor")
public class MockSmsVendorController {
    private final Random random = new Random();

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> send(@RequestBody Map<String, String> body){
        boolean success = random.nextInt(10)>1;

        return ResponseEntity.ok(
                Map.of("status", success? "SENT": "FAILED")
        );
    }
}
