package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping()
    public List<Expense> findAll() {
        return expenseService.findAll();
    }

    @PostMapping()
    public Expense create(@RequestBody @Valid CreateExpenseDTO data) {
        return expenseService.create(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return expenseService.delete(id);
    }
}
