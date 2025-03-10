package com.restaurant.orderfood.controller;

import com.restaurant.orderfood.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> request) {
        Integer tableId = (Integer) request.get("tableId");
        String phoneNumber = (String) request.get("phoneNumber");

        if (tableId == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Table ID is required");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String invoiceId = paymentService.processPayment(tableId, phoneNumber);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("invoiceId", invoiceId);
            response.put("message", "Payment processed successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}