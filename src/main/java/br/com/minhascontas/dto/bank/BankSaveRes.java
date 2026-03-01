package br.com.minhascontas.dto.bank;

import br.com.minhascontas.model.Banco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankSaveRes {

    private UUID id;
    private String name;
    private BigDecimal limitValue;
    private String dueDate;
    private String closingDate;
    private String color;
    private LocalDateTime createdAt;

    public static BankSaveRes fromEntity(Banco banco) {
        BankSaveRes res = new BankSaveRes();
        res.setId(banco.getId());
        res.setName(banco.getName());
        res.setLimitValue(banco.getLimitValue());
        res.setDueDate(banco.getDueDate());
        res.setClosingDate(banco.getClosingDate());
        res.setColor(banco.getColor());
        res.setCreatedAt(banco.getCreatedAt());
        return res;
    }
}
