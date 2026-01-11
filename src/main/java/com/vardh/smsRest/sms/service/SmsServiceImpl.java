//package com.vardh.smsRest.sms.service;
//
//import com.vardh.smsRest.sms.dto.SmsRequest;
//import com.vardh.smsRest.sms.dto.SmsResponse;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class SmsServiceImpl implements SmsService {
//
//    private final RestTemplate restTemplate;
//    private final BlocklistService blocklistService;
//
//    public SmsServiceImpl(
//            RestTemplate restTemplate,
//            BlocklistService blocklistService
//    ) {
//        this.restTemplate = restTemplate;
//        this.blocklistService = blocklistService;
//    }
//
//
//    @Override
//    public SmsResponse sendSms(SmsRequest request) {
//        if (blocklistService.isUserBlocked(request.getPhoneNumber())) {
//            return new SmsResponse("BLOCKED", null);
//        }
//
//        // call mock vendor
//        Map<String, String> vendorRequest = Map.of(
//                "phone", request.getPhoneNumber(),
//                "message", request.getMessage()
//        );
//
//        Map response = restTemplate.postForObject(
//                "http://localhost:8080/mock-sms-vendor/send",
//                vendorRequest,
//                Map.class
//        );
//
//        String status = response != null
//                ? response.getOrDefault("status", "FAILED").toString()
//                : "FAILED";
//
//        return new SmsResponse(
//                status,
//                UUID.randomUUID().toString()
//        );
//    }
//}


package com.vardh.smsRest.sms.service;

import com.vardh.smsRest.sms.dto.SmsEvent;
import com.vardh.smsRest.sms.dto.SmsRequest;
import com.vardh.smsRest.sms.dto.SmsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class SmsServiceImpl implements SmsService {

    private final RestTemplate restTemplate;
    private final BlocklistService blocklistService;
    private final SmsEventPublisher smsEventPublisher;

    public SmsServiceImpl(
            RestTemplate restTemplate,
            BlocklistService blocklistService,
            SmsEventPublisher smsEventPublisher
    ) {
        this.restTemplate = restTemplate;
        this.blocklistService = blocklistService;
        this.smsEventPublisher = smsEventPublisher;
    }

    @Override
    public SmsResponse sendSms(SmsRequest request) {
        if (blocklistService.isUserBlocked(request.getPhoneNumber())) {
            // publish a BLOCKED event optionally, or just return blocked response
            SmsEvent blockedEvent = new SmsEvent(request.getPhoneNumber(), request.getMessage(), "BLOCKED");
            smsEventPublisher.publish(blockedEvent);
            return new SmsResponse("BLOCKED", null);
        }

        // call mock vendor
        Map<String, String> vendorRequest = Map.of(
                "phone", request.getPhoneNumber(),
                "message", request.getMessage()
        );

        Map response = restTemplate.postForObject(
                "http://localhost:8080/mock-sms-vendor/send",
                vendorRequest,
                Map.class
        );

        String status = response != null
                ? response.getOrDefault("status", "FAILED").toString()
                : "FAILED";

        // publish event asynchronously
        SmsEvent event = new SmsEvent(request.getPhoneNumber(), request.getMessage(), status);
        smsEventPublisher.publish(event);

        return new SmsResponse(
                status,
                UUID.randomUUID().toString()
        );
    }
}

