package br.com.minhascontas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bancos")
public class Banco {
    @Id
    private Long id;
    private String name;
    private BigDecimal limitValue; // 'limit' is a reserved keyword in some DBs
    private String dueDate;
    private String closingDate;
    private String color;
}
