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

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CommitmentService commitmentService;

    public Page<Payment> findAllByCommitmentId(long commitment_id, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "id"));
        return paymentRepository.findAllByCommitmentId(commitment_id, pageable);
    }

    public Payment findById(long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
    }

    public Payment create(CreatePaymentDTO data) {
        Commitment commitment = commitmentService.findById(data.commitment_id());

        float totalPayments = paymentRepository.sumPaymentsByCommitmentId(commitment.getId());

        if(totalPayments + data.value() > commitment.getValue()) {
            throw new DataIntegrityViolationException("O valor total dos pagamentos excede o valor do empenho");
        }

        Payment payment = new Payment(data.date(), data.value(), data.note());
        payment.setCommitment(commitment);

        return paymentRepository.save(payment);
    }

    public void delete(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        paymentRepository.deleteById(id);
    }
}
