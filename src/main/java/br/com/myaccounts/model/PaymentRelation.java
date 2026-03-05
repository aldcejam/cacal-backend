package br.com.myaccounts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_relations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "payment_id")
})
@EqualsAndHashCode(callSuper = true)
public class PaymentRelation extends BaseEntity<UUID> {

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(name = "person_name")
    private String personName;

}
