package com.estapar.parking.controller;

import com.estapar.parking.request.RevenueRequest;
import com.estapar.parking.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @GetMapping
    public ResponseEntity<?> getRevenue(@RequestBody RevenueRequest request) {
        try {


            return ResponseEntity.ok(revenueService.getRevenue(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
