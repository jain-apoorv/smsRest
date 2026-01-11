package com.vardh.smsRest.sms.controller;

import com.vardh.smsRest.sms.service.BlocklistService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/blocklist")
@Validated
public class BlocklistController {

    private final BlocklistService blocklistService;

    public BlocklistController(BlocklistService blocklistService) {
        this.blocklistService = blocklistService;
    }

    @PostMapping("/{phoneNumber}")
    public ResponseEntity<String> block(@PathVariable String phoneNumber) {
        blocklistService.block(phoneNumber);
        return ResponseEntity.ok("User blocked successfully");
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<String> unblock(@PathVariable String phoneNumber) {
        blocklistService.unblock(phoneNumber);
        return ResponseEntity.ok("User unblocked successfully");
    }

    // ✅ IS BLOCKED
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Boolean> isBlocked(
            @PathVariable
            @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Invalid phone number")
            String phoneNumber) {

        return ResponseEntity.ok(blocklistService.isUserBlocked(phoneNumber));
    }
}
