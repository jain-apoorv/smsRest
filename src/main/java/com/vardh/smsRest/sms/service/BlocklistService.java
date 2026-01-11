package com.vardh.smsRest.sms.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlocklistService {

    private final RedisTemplate<String, String> redisTemplate;

    public BlocklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(String phoneNumber) {
        return "blocked:user:" + phoneNumber;
    }

    public boolean isUserBlocked(String phoneNumber) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getKey(phoneNumber)));
    }

    // Optional: for testing/demo
    public void block(String phoneNumber) {
        redisTemplate.opsForValue().set(getKey(phoneNumber), "1");
    }

    public void unblock(String phoneNumber) {
        redisTemplate.delete(getKey(phoneNumber));
    }
}
