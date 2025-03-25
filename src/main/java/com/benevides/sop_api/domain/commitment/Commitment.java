package com.benevides.sop_api.domain.commitment;

import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.payment.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "commitment")
@Entity(name = "commitment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Commitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private float value;

    @Column(unique = true, nullable = false)
    private String commitment_number;

    private String note;

    @OneToMany(mappedBy = "commitment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public Commitment(Date date, float value, String note) {
        this.date = date;
        this.value = value;
        this.note = note;
    }
}
