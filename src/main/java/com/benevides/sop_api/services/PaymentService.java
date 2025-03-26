package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.payment.CreatePaymentDTO;
import com.benevides.sop_api.domain.payment.Payment;
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
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CommitmentService commitmentService;
    @Autowired
    private ExpenseService expenseService;

    public Page<Payment> findAllByCommitmentId(long commitment_id, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        return paymentRepository.findAllByCommitmentId(commitment_id, pageable);
    }

    public Payment findById(long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
    }

    public Payment create(CreatePaymentDTO data) {
        Commitment commitment = commitmentService.findById(data.commitment_id());

        BigDecimal totalPayments = paymentRepository.sumPaymentsByCommitmentId(commitment.getId());

        if(totalPayments.add(data.value()).compareTo(commitment.getValue()) > 0) {
            throw new DataIntegrityViolationException("Não é possível criar esse pagamento, pois assim o valor total dos pagamentos excede o valor do empenho");
        }

        String paymentNumber = generatePaymentNumber();
        Payment payment = new Payment(data.date(), data.value(), data.note());
        payment.setCommitment(commitment);
        payment.setPayment_number(paymentNumber);

        Payment savedPayment = paymentRepository.save(payment);

        expenseService.updateStatus(commitment.getExpense().getId());

        return savedPayment;
    }

    public void delete(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        paymentRepository.deleteById(id);
        expenseService.updateStatus(payment.getCommitment().getExpense().getId());
    }

    private String generatePaymentNumber() {
        String year = String.valueOf(java.time.Year.now().getValue());
        long nextSequence = paymentRepository.findMaxPaymentSequenceByYear(year) + 1;
        return "%sNP%04d".formatted(year, nextSequence);
    }
}
