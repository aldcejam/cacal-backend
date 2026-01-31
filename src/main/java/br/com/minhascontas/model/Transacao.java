package br.com.minhascontas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacoes")
public class Transacao {
    @Id
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Cartao card;

    private String description;
    private String category;
    private BigDecimal value;
    private String parcels;
    private BigDecimal total;
}
