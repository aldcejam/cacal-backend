package br.com.myaccounts.dto.bank;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Bank;
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

    public static BankSaveRes fromEntity(Bank bank) {
        BankSaveRes res = new BankSaveRes();
        res.setId(bank.getId());
        res.setName(bank.getName());
        res.setLimitValue(bank.getLimitValue());
        res.setDueDate(bank.getDueDate());
        res.setClosingDate(bank.getClosingDate());
        res.setColor(bank.getColor());
        res.setCreatedAt(bank.getCreatedAt());
        return res;
    }
}
