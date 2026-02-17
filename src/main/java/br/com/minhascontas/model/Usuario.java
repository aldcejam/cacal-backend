package br.com.minhascontas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
@EqualsAndHashCode(callSuper = true)
public class Usuario extends BaseEntity<UUID> {
    private String name;
    private String email;
    private String password;
    private String role; // For simplicity, single role for now. Could be Set<String> roles.
}
