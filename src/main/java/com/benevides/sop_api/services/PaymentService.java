package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.payment.CreatePaymentDTO;
import com.benevides.sop_api.domain.payment.Payment;
import com.benevides.sop_api.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CommitmentService commitmentService;

    public List<Payment> findAllByCommitmentId(long commitment_id) {
        return paymentRepository.findAllByCommitmentId(commitment_id);
    }

    public Payment findById(long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    public Payment create(CreatePaymentDTO data) {
        Commitment commitment = commitmentService.findById(data.commitment_id());

        float totalPayments = paymentRepository.sumPaymentsByCommitmentId(commitment.getId());

        if(totalPayments + data.value() > commitment.getValue()) {
            throw new DataIntegrityViolationException("The total value of payments exceeds the commitment value");
        }

        Payment payment = new Payment(data.date(), data.value(), data.note());
        payment.setCommitment(commitment);

        return paymentRepository.save(payment);
    }

    public void delete(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        paymentRepository.deleteById(id);
    }
}
