package com.benevides.sop_api.domain.commitment;

import com.benevides.sop_api.domain.expense.Expense;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Table(name = "commitment")
@Entity(name = "commitment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Commitment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date date;
    private float value;
    private String note;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public Commitment(Date date, float value, String note) {
        this.date = date;
        this.value = value;
        this.note = note;
    }
}
