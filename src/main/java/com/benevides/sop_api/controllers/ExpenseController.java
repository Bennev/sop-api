package com.benevides.sop_api.controllers;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO;
import com.benevides.sop_api.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = ".:Despesas:.")
@RestController
@RequestMapping("expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna todos as despesas paginadas"
    )
    public ResponseEntity<Page<GetExpensesWithCommitmentCountDTO>> findAllWithCommitmentCount(@RequestParam(defaultValue = "0") int page) {
        Page<GetExpensesWithCommitmentCountDTO> expenses = expenseService.findAllWithCommitmentCount(page);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método retorna uma despesa com base no id"
    )
    public ResponseEntity<Expense> findById(@PathVariable long id) {
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método cria uma nova despesa"
    )
    public ResponseEntity<Expense> create(@RequestBody @Valid CreateExpenseDTO data) {
        Expense expense = expenseService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @DeleteMapping("/{id}")
    @Operation(
            security = {@SecurityRequirement(name = "bearer-key")},
            description = "Este método deleta uma despesa com base no id"
    )
    public ResponseEntity<Void> delete(@PathVariable long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
