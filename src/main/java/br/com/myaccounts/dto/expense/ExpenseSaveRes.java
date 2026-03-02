package br.com.myaccounts.dto.expense;

import br.com.myaccounts.model.RecurringExpense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSaveRes {

    private UUID id;
    private String paymentMethod;
    private String description;
    private String category;
    private BigDecimal value;
    private UserSummary user;
    private LocalDateTime createdAt;

    public static ExpenseSaveRes fromEntity(RecurringExpense gasto) {
        ExpenseSaveRes res = new ExpenseSaveRes();
        res.setId(gasto.getId());
        res.setPaymentMethod(gasto.getPaymentMethod());
        res.setDescription(gasto.getDescription());
        res.setCategory(gasto.getCategory());
        res.setValue(gasto.getValue());
        res.setCreatedAt(gasto.getCreatedAt());
        if (gasto.getUser() != null) {
            res.setUser(new UserSummary(gasto.getUser().getId(), gasto.getUser().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }
}
