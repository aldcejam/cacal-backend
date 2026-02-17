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
    private String lastDigits;
    private BigDecimal limitValue;
    private BigDecimal available;
    private LocalDate dueDate;
    private LocalDate closingDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Banco bank;
}
