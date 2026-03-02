package br.com.myaccounts.dto.income;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSaveRes {

    private UUID id;
    private String description;
    private String category;
    private BigDecimal value;
    private Integer receiptDay;
    private UserSummary user;
    private LocalDateTime createdAt;

    public static IncomeSaveRes fromEntity(Income income) {
        IncomeSaveRes res = new IncomeSaveRes();
        res.setId(income.getId());
        res.setDescription(income.getDescription());
        res.setCategory(income.getCategory());
        res.setValue(income.getValue());
        res.setReceiptDay(income.getReceiptDay());
        res.setCreatedAt(income.getCreatedAt());
        if (income.getUser() != null) {
            res.setUser(new UserSummary(income.getUser().getId(), income.getUser().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }
}
