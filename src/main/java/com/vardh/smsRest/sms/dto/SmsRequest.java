package com.vardh.smsRest.sms.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SmsRequest {

    @NotBlank(message = "phone number should be there")
    @Pattern(
            regexp = "^\\+?\\d{10,15}$",
            message = "Invalid Phone Format"
    )
    private String phoneNumber;

    @NotBlank(message = "message should not be empty")
    private String message;

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
