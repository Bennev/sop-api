package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.domain.commitment.GetCommitmentsWithPaymentCountDTO;
import com.benevides.sop_api.services.CommitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = ".:Empenhos:.")
@RestController
@RequestMapping("commitment")
public class CommitmentController {
    @Autowired
    private CommitmentService commitmentService;

    @GetMapping("/expense/{expense_id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna todos os empenhos paginados com base no id da despesa"
    )
    public ResponseEntity<Page<GetCommitmentsWithPaymentCountDTO>> findAllWithPaymentCountByExpenseId(
            @PathVariable long expense_id,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<GetCommitmentsWithPaymentCountDTO> commitments = commitmentService.findAllWithPaymentCountByExpenseId(expense_id, page);
        return ResponseEntity.ok(commitments);
    }

    @GetMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna um empenho com base no id"
    )
    public ResponseEntity<Commitment> findById(@PathVariable long id) {
        Commitment commitment = commitmentService.findById(id);
        return ResponseEntity.ok(commitment);
    }

    @PostMapping
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método cria um novo empenho"
    )
    public ResponseEntity<Commitment> create(@RequestBody @Valid CreateCommitmentDTO data) {
        Commitment commitment = commitmentService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(commitment);
    }

    @DeleteMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método deleta um empenho com base no id"
    )
    public ResponseEntity<Void> delete(@PathVariable long id) {
        commitmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
