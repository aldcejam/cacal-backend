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
@Table(name = "receitas")
@EqualsAndHashCode(callSuper = true)
public class Receita extends BaseEntity<UUID> {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer diaRecebimento;
}
