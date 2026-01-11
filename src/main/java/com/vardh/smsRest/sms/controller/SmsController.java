package com.vardh.smsRest.sms.controller;

import com.vardh.smsRest.sms.dto.SmsRequest;
import com.vardh.smsRest.sms.dto.SmsResponse;
import com.vardh.smsRest.sms.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/v1/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService){
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendSMS(@Valid @RequestBody SmsRequest smsRq){

        SmsResponse response = smsService.sendSms(smsRq);

        return ResponseEntity.accepted().body(response);

    }
}
