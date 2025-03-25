package com.benevides.sop_api.domain.payment;

import com.benevides.sop_api.domain.commitment.Commitment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "payment")
@Entity(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private float value;

    @Column(unique = true, nullable = false)
    private String payment_number;

    private String note;

    @ManyToOne
    @JoinColumn(name = "commitment_id")
    private Commitment commitment;

    public Payment(Date date, float value, String note){
        this.date = date;
        this.value = value;
        this.note = note;
    }
}
