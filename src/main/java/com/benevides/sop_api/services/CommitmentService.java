package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.CreateCommitmentDTO;
import com.benevides.sop_api.domain.commitment.GetCommitmentsWithPaymentCountDTO;
import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.repositories.CommitmentRepository;
import com.benevides.sop_api.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CommitmentService {
    @Autowired
    private CommitmentRepository commitmentRepository;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private PaymentRepository paymentRepository;

    public Page<GetCommitmentsWithPaymentCountDTO> findAllWithPaymentCountByExpenseId(long expense_id, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return commitmentRepository.findAllWithPaymentCountByExpenseId(expense_id, pageable);
    }

    public Commitment findById(long id) {
        return commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empenho não encontrado"));
    }

    public Commitment create(CreateCommitmentDTO data) {
        Expense expense = expenseService.findById(data.expense_id());

        BigDecimal totalCommitments = commitmentRepository.sumCommitmentsByExpenseId(expense.getId());

        if(totalCommitments.add(data.value()).compareTo(expense.getValue()) > 0) {
            throw new DataIntegrityViolationException("Não é possível criar esse empenho, pois assim o valor total dos empenhos excede o valor da despesa");
        }

        String commitmentNumber = generateCommitmentNumber();
        Commitment commitment = new Commitment(data.date(), data.value(), data.note());
        commitment.setExpense(expense);
        commitment.setCommitment_number(commitmentNumber);

        Commitment savedCommitment = commitmentRepository.save(commitment);

        expenseService.updateStatus(expense.getId());

        return savedCommitment;
    }

    public void delete(long id) {
        Commitment commitment = commitmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empenho não encontrado"));

        boolean hasPayments = paymentRepository.existsByCommitmentId(id);
        if (hasPayments) {
            throw new DataIntegrityViolationException("Não é possível excluir empenho com pagamentos");
        }
        commitmentRepository.deleteById(id);
        expenseService.updateStatus(commitment.getExpense().getId());
    }

    private String generateCommitmentNumber() {
        String year = String.valueOf(java.time.Year.now().getValue());
        long nextSequence = commitmentRepository.findMaxCommitmentSequenceByYear(year) + 1;
        return "%sNE%04d".formatted(year, nextSequence);
    }
}
