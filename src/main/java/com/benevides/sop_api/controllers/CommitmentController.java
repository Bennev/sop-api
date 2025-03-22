package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.services.CommitmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("commitment")
public class CommitmentController {
    @Autowired
    private CommitmentService commitmentService;

    @GetMapping("/expense/{expense_id}")
    public ResponseEntity<List<Commitment>> findAllByExpense(@PathVariable UUID expense_id) {
        List<Commitment> commitments = commitmentService.findAllByExpenseId(expense_id);
        return ResponseEntity.ok(commitments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commitment> findById(@PathVariable UUID id) {
        Commitment commitment = commitmentService.findById(id);
        return ResponseEntity.ok(commitment);
    }

    @PostMapping()
    public Commitment create(@RequestBody @Valid CreateCommitmentDTO data) {
        return commitmentService.create(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        commitmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
