package br.com.minhascontas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
@EqualsAndHashCode(callSuper = true)
public class Transacao extends BaseEntity<UUID> {

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Cartao card;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private String parcels;

    @Column(nullable = false)
    private BigDecimal total;
}
