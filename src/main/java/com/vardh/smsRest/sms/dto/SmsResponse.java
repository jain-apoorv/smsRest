package com.vardh.smsRest.sms.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class SmsResponse {
    private String status;
    private String messageId;
    private Instant timestamp;

    public SmsResponse(String status, String messageId){
        this.status = status;
        this.messageId = messageId;
        this.timestamp = Instant.now();
    }

    public String getStatus(){
        return status;
    }

    public String getMessageId(){
        return messageId;
    }

    public Instant getTimestamp(){
        return timestamp;
    }

}
