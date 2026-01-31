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
@Table(name = "gastos_recorrentes")
public class GastoRecorrente {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    private String pagamento;
    private String descricao;
    private String categoria;
    private BigDecimal valor;
}
