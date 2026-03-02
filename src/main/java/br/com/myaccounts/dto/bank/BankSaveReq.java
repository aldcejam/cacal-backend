package br.com.myaccounts.dto.bank;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Bank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankSaveReq {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Limit is required")
    @Positive(message = "Limite deve ser positivo")
    private BigDecimal limitValue;

    @NotBlank(message = "Due date is required")
    private String dueDate;

    @NotBlank(message = "Closing date is required")
    private String closingDate;

    @NotBlank(message = "Color is required")
    private String color;

    public Bank toEntity() {
        Bank bank = new Bank();
        bank.setName(this.name);
        bank.setLimitValue(this.limitValue);
        bank.setDueDate(this.dueDate);
        bank.setClosingDate(this.closingDate);
        bank.setColor(this.color);
        return bank;
    }
}
