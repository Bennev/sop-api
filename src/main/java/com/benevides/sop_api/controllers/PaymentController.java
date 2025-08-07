package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.payment.CreatePaymentDTO;
import com.benevides.sop_api.domain.payment.Payment;
import com.benevides.sop_api.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = ".:Pagamentos:.")
@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/commitment/{commitment_id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna todos os pagamentos paginados com base no id do empenho"
    )
    public ResponseEntity<Page<Payment>> findAllByCommitmentId(
            @PathVariable long commitment_id,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<Payment> payments = paymentService.findAllByCommitmentId(commitment_id, page);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna um pagamento com base no id"
    )
    public ResponseEntity<Payment> findById(@PathVariable long id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método cria um novo pagamento"
    )
    public ResponseEntity<Payment> create(@RequestBody @Valid CreatePaymentDTO data) {
        Payment payment = paymentService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @DeleteMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método deleta um pagamento com base no id"
    )
    public ResponseEntity<Void> delete(@PathVariable long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
