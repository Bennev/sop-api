package com.benevides.sop_api.domain.expense;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Table(name = "expense")
@Entity(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ExpenseType type;
    private Date protocol_date;
    private Date due_date;
    private String creditor;
    private String description;
    private float value;

    public Expense(ExpenseType type, Date protocol_date, Date due_date, String creditor, String description, float value) {
        this.type = type;
        this.protocol_date = protocol_date;
        this.due_date = due_date;
        this.creditor = creditor;
        this.description = description;
        this.value = value;
    }
}
