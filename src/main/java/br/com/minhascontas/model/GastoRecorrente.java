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
@Table(name = "gastos_recorrentes")
@EqualsAndHashCode(callSuper = true)
public class GastoRecorrente extends BaseEntity<UUID> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    private String pagamento;
    private String descricao;
    private String categoria;
    private BigDecimal valor;
}
