package br.com.minhascontas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "bancos")
@EqualsAndHashCode(callSuper = true)
public class Banco extends BaseEntity<UUID> {
    private String name;
    private BigDecimal limitValue; // 'limit' is a reserved keyword in some DBs
    private String dueDate;
    private String closingDate;
    private String color;
}
