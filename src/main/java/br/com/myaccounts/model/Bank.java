package br.com.myaccounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "banks")
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity<UUID> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal limitValue;

    @Column(nullable = false)
    private String dueDate;

    @Column(nullable = false)
    private String closingDate;

    @Column(nullable = false)
    private String color;
}
