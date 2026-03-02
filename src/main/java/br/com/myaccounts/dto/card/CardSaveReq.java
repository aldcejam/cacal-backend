package br.com.myaccounts.dto.card;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardSaveReq {

    @NotBlank(message = "Last digits are required")
    private String lastDigits;

    @NotNull(message = "Limit is required")
    @Positive(message = "Limite deve ser positivo")
    private BigDecimal limitValue;

    @NotNull(message = "Disponível é obrigatório")
    private BigDecimal available;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    @NotNull(message = "Closing date is required")
    private LocalDate closingDate;

    @NotNull(message = "User is required")
    private UUID userId;

    @NotNull(message = "Bank é obrigatório")
    private UUID bankId;

    public Card toEntity(User user, Bank bank) {
        Card card = new Card();
        card.setLastDigits(this.lastDigits);
        card.setLimitValue(this.limitValue);
        card.setAvailable(this.available);
        card.setDueDate(this.dueDate);
        card.setClosingDate(this.closingDate);
        card.setUser(user);
        card.setBank(bank);
        return card;
    }
}
