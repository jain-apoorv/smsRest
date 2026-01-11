package com.vardh.smsRest.sms.dto;

import java.time.Instant;

public class SmsEvent {

    private String phoneNumber;
    private String message;
    private String status;   // SUCCESS / FAILED
    private Instant timestamp;

    public SmsEvent() {}

    public SmsEvent(String phoneNumber, String message, String status) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now();
    }

    public String getPhoneNumber() { return phoneNumber; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public Instant getTimestamp() { return timestamp; }
}
