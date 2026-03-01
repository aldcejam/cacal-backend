package br.com.minhascontas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartoes")
@EqualsAndHashCode(callSuper = true)
public class Cartao extends BaseEntity<UUID> {

    @Column(nullable = false)
    private String lastDigits;

    @Column(nullable = false)
    private BigDecimal limitValue;

    @Column(nullable = false)
    private BigDecimal available;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDate closingDate;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Banco bank;
}
