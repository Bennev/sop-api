package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommitmentService {
    @Autowired
    private CommitmentRepository commitmentRepository;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Commitment> findAllByExpenseId(UUID expense_id) {
        return commitmentRepository.findAllByExpenseId((expense_id));
    }

    public Commitment findById(UUID id) {
        return commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commitment not found"));
    }

    public Commitment create(CreateCommitmentDTO data) {
        Expense expense = expenseService.findById(data.expense_id());

        float totalCommitments = commitmentRepository.sumCommitmentsByExpenseId(expense.getId());

        if(totalCommitments + data.value() > expense.getValue()) {
            throw new DataIntegrityViolationException("The total value of commitments exceeds the expense value");
        }

        Commitment commitment = new Commitment(data.date(), data.value(), data.note());
        commitment.setExpense(expense);

        return commitmentRepository.save(commitment);
    }

    public void delete(UUID id) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commitment not found"));

        boolean hasPayments = paymentRepository.existsByCommitmentId(id);
        if (hasPayments) {
            throw new DataIntegrityViolationException("Cannot delete Commitment with active Payments");
        }
        commitmentRepository.deleteById(id);
    }
}
