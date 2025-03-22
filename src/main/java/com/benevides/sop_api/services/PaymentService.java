package com.benevides.sop_api.services;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.payment.CreatePaymentDTO;
import com.benevides.sop_api.domain.payment.Payment;
import com.benevides.sop_api.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CommitmentService commitmentService;

    public List<Payment> findAllByCommitment_Id(UUID commitment_id) {
        return paymentRepository.findAllByCommitment_Id(commitment_id);
    }

    public Payment findById(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    public Payment create(CreatePaymentDTO data) {
        Commitment commitment = commitmentService.findById(data.commitment_id());
        Payment payment = new Payment(data.date(), data.value(), data.note());
        payment.setCommitment(commitment);

        return paymentRepository.save(payment);
    }

    public void delete(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        paymentRepository.deleteById(id);
    }
}
