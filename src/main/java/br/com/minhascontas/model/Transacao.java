package br.com.minhascontas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @JoinColumn(name = "card_id")
    private Cartao card;

    private String description;
    private String category;
    private BigDecimal value;
    private String parcels;
    private BigDecimal total;
}
