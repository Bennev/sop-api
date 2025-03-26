package com.benevides.sop_api.domain.commitment;

import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.payment.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    private BigDecimal value;

    @Column(unique = true, nullable = false)
    private String commitment_number;

    private String note;

    @OneToMany(mappedBy = "commitment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public Commitment(Date date, BigDecimal value, String note) {
        this.date = date;
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.note = note;
    }
}
