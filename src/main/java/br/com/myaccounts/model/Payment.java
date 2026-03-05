package br.com.myaccounts.model;

import br.com.myaccounts.model.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity<UUID> {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethodType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User ownerUser;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentRelation relation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> details;
}
