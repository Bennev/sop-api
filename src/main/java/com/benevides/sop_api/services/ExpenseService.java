package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.expense.CreateExpenseDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.expense.ExpenseType;
import com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    CommitmentRepository commitmentRepository;

    public Page<GetExpensesWithCommitmentCountDTO> findAllWithCommitmentCount(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "id"));
        return expenseRepository.findAllWithCommitmentCount(pageable);
    }

    public Expense findById(long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));
    }

    public Expense create(CreateExpenseDTO data) {
        ExpenseType expenseType;
        try {
            expenseType = ExpenseType.valueOf(data.type());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de despesa inválido");
        }
        Expense newExpense = new Expense(expenseType,data.protocol_date(),data.due_date(),data.creditor(),data.description(),data.value());
        return expenseRepository.save(newExpense);
    }

    public void delete(long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

        boolean hasCommitments = commitmentRepository.existsByExpenseId(id);
        if (hasCommitments) {
            throw new DataIntegrityViolationException("Não é possível excluir despesa com empenhos");
        }

        expenseRepository.deleteById(id);
    }
}
