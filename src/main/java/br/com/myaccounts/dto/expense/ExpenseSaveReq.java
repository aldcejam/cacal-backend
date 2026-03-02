package br.com.myaccounts.dto.expense;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.RecurringExpense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSaveReq {

    @NotNull(message = "User is required")
    private UUID userId;

    @NotBlank(message = "Forma de paymentMethod é obrigatória")
    private String paymentMethod;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private BigDecimal value;

    public RecurringExpense toEntity(User user) {
        RecurringExpense gasto = new RecurringExpense();
        gasto.setUser(user);
        gasto.setPaymentMethod(this.paymentMethod);
        gasto.setDescription(this.description);
        gasto.setCategory(this.category);
        gasto.setValue(this.value);
        return gasto;
    }
}
