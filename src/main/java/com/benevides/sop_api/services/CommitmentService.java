package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommitmentService {
    @Autowired
    private CommitmentRepository commitmentRepository;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private PaymentRepository paymentRepository;

    public Page<Commitment> findAllPaginatedByExpenseId(long expense_id, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return commitmentRepository.findAllByExpenseId(expense_id, pageable);
    }

    public Commitment findById(long id) {
        return commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empenho não encontrado"));
    }

    public Commitment create(CreateCommitmentDTO data) {
        Expense expense = expenseService.findById(data.expense_id());

        float totalCommitments = commitmentRepository.sumCommitmentsByExpenseId(expense.getId());

        if(totalCommitments + data.value() > expense.getValue()) {
            throw new DataIntegrityViolationException("O valor total dos empenhos excede o valor da despesa");
        }

        Commitment commitment = new Commitment(data.date(), data.value(), data.note());
        commitment.setExpense(expense);

        return commitmentRepository.save(commitment);
    }

    public void delete(long id) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empenho não encontrado"));

        boolean hasPayments = paymentRepository.existsByCommitmentId(id);
        if (hasPayments) {
            throw new DataIntegrityViolationException("Não é possível excluir empenho com pagamentos");
        }
        commitmentRepository.deleteById(id);
    }
}
