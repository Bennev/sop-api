package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.domain.commitment.GetCommitmentsWithPaymentCountDTO;
import com.benevides.sop_api.services.CommitmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("commitment")
public class CommitmentController {
    @Autowired
    private CommitmentService commitmentService;

    @GetMapping("/expense/{expense_id}")
    public ResponseEntity<Page<GetCommitmentsWithPaymentCountDTO>> findAllWithPaymentCountByExpenseId(
            @PathVariable long expense_id,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<GetCommitmentsWithPaymentCountDTO> commitments = commitmentService.findAllWithPaymentCountByExpenseId(expense_id, page);
        return ResponseEntity.ok(commitments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commitment> findById(@PathVariable long id) {
        Commitment commitment = commitmentService.findById(id);
        return ResponseEntity.ok(commitment);
    }

    @PostMapping
    public ResponseEntity<Commitment> create(@RequestBody @Valid CreateCommitmentDTO data) {
        Commitment commitment = commitmentService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(commitment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        commitmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
