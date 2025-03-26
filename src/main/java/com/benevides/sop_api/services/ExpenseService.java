package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.expense.*;
import com.benevides.sop_api.domain.payment.Payment;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    CommitmentRepository commitmentRepository;

    public Page<GetExpensesWithCommitmentCountDTO> findAllWithCommitmentCount(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
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
        String protocolNumber = generateProtocolNumber();
        Expense expense = new Expense(expenseType,data.protocol_date(),data.due_date(),data.creditor(),data.description(),data.value());
        expense.setProtocol_number(protocolNumber);
        expense.setStatus(ExpenseStatus.WAITING_COMMITMENT);
        return expenseRepository.save(expense);
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

    private String generateProtocolNumber() {
        String yearMonth = java.time.YearMonth.now().toString();
        Long maxSequence = expenseRepository.findMaxProtocolSequenceByMonth(yearMonth);
        long nextSequence = (maxSequence != null ? maxSequence : 0) + 1;

        return "43022.%06d/%s".formatted(nextSequence, yearMonth);
    }

    @Transactional
    public void updateStatus(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));

        BigDecimal totalCommitments = expense.getCommitments().stream()
                .map(Commitment::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPayments = expense.getCommitments().stream()
                .flatMap(commitment -> commitment.getPayments().stream())
                .map(Payment::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        if (totalCommitments.equals(BigDecimal.ZERO)) {
            expense.setStatus(ExpenseStatus.WAITING_COMMITMENT);
        } else if (totalCommitments.compareTo(expense.getValue()) < 0) {
            expense.setStatus(ExpenseStatus.PARTIALLY_COMMITTED);
        } else if (totalCommitments.compareTo(expense.getValue()) == 0 && totalPayments.equals(BigDecimal.ZERO)) {
            expense.setStatus(ExpenseStatus.WAITING_PAYMENT);
        } else if (totalCommitments.compareTo(expense.getValue()) == 0 && totalPayments.compareTo(expense.getValue()) < 0) {
            expense.setStatus(ExpenseStatus.PARTIALLY_PAID);
        } else if (totalPayments.compareTo(expense.getValue()) == 0) {
            expense.setStatus(ExpenseStatus.PAID);
        }

        expenseRepository.save(expense);
    }
}
