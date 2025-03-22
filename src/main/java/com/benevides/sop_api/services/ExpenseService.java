package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Expense create(CreateExpenseDTO data) {
        Expense newExpense = new Expense(data.type(),data.protocol_date(),data.due_date(),data.creditor(),data.description(),data.value());
        return expenseRepository.save(newExpense);
    }

    public ResponseEntity<Void> delete(UUID id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
