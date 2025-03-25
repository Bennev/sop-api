package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO;
import com.benevides.sop_api.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<Page<GetExpensesWithCommitmentCountDTO>> findAllWithCommitmentCount(@RequestParam(defaultValue = "0") int page) {
        Page<GetExpensesWithCommitmentCountDTO> expenses = expenseService.findAllWithCommitmentCount(page);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable long id) {
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody @Valid CreateExpenseDTO data) {
        Expense expense = expenseService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
