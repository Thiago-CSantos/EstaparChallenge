package com.estapar.parking.controller;

import com.estapar.parking.request.WebhookRequest;
import com.estapar.parking.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WebhookController {

    @Autowired
    private WebhookService webhookService;

    @PostMapping("webhook")
    public ResponseEntity<?> webhook(@RequestBody WebhookRequest payload) {
        try {

            if (payload.eventType() == null) {
                return ResponseEntity.badRequest().body("eventType ausente no payload");
            }

            switch (payload.eventType()) {
                case "ENTRY" -> webhookService.handleEntry(payload);
                case "PARKED" -> webhookService.handleParked(payload);
                case "EXIT" -> webhookService.handleExit(payload);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing webhook: " + e.getMessage());
        }
    }

}
