package com.benevides.sop_api.domain.expense;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Table(name = "expense")
@Entity(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType type;

    @Column(nullable = false)
    private Date protocol_date;

    @Column(nullable = false)
    private Date due_date;

    @Column(nullable = false)
    private String creditor;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(unique = true, nullable = false)
    private String protocol_number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    private String description;

    @OneToMany(mappedBy = "expense", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Commitment> commitments;

    public Expense(ExpenseType type, Date protocol_date, Date due_date, String creditor, String description, BigDecimal value) {
        this.type = type;
        this.protocol_date = protocol_date;
        this.due_date = due_date;
        this.creditor = creditor;
        this.description = description;
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }
}
