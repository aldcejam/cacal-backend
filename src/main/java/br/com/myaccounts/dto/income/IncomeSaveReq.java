package br.com.myaccounts.dto.income;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Income;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class IncomeSaveReq {

    @NotNull(message = "User is required")
    private UUID userId;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private BigDecimal value;

    @NotNull(message = "Receipt day is required")
    @Min(value = 1, message = "Day must be between 1 and 31")
    @Max(value = 31, message = "Day must be between 1 and 31")
    private Integer receiptDay;

    public Income toEntity(User user) {
        Income income = new Income();
        income.setUser(user);
        income.setDescription(this.description);
        income.setCategory(this.category);
        income.setValue(this.value);
        income.setReceiptDay(this.receiptDay);
        return income;
    }
}
