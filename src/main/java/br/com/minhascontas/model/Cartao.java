package br.com.minhascontas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartoes")
public class Cartao {
    @Id
    private String id;
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
