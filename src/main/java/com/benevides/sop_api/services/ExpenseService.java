package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    CommitmentRepository commitmentRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Expense findById(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));
    }

    public Expense create(CreateExpenseDTO data) {
        Expense newExpense = new Expense(data.type(),data.protocol_date(),data.due_date(),data.creditor(),data.description(),data.value());
        return expenseRepository.save(newExpense);
    }

    public void delete(UUID id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        boolean hasCommitments = commitmentRepository.existsByExpenseId(id);
        if (hasCommitments) {
            throw new DataIntegrityViolationException("Cannot delete Expense with active Commitments");
        }

        expenseRepository.deleteById(id);
    }
}
