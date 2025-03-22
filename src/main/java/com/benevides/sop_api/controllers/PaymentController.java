package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.payment.CreatePaymentDTO;
import com.benevides.sop_api.domain.payment.Payment;
import com.benevides.sop_api.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/commitment/{commitment_id}")
    public ResponseEntity<List<Payment>> findAllByCommitmentId(@PathVariable UUID commitment_id) {
        List<Payment> payments = paymentService.findAllByCommitmentId(commitment_id);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable UUID id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping()
    public Payment create(@RequestBody @Valid CreatePaymentDTO data) {
        return paymentService.create(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
