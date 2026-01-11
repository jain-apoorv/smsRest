package com.vardh.smsRest.sms.service;

import com.vardh.smsRest.sms.dto.SmsRequest;
import com.vardh.smsRest.sms.dto.SmsResponse;

public interface SmsService {
    SmsResponse sendSms(SmsRequest smsRequest);
}
