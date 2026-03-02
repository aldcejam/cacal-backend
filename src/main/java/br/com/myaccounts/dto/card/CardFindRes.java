package br.com.myaccounts.dto.card;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardFindRes {

    private UUID id;
    private String lastDigits;
    private BigDecimal limitValue;
    private BigDecimal available;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private UserSummary user;
    private BankSummary bank;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CardFindRes fromEntity(Card card) {
        CardFindRes res = new CardFindRes();
        res.setId(card.getId());
        res.setLastDigits(card.getLastDigits());
        res.setLimitValue(card.getLimitValue());
        res.setAvailable(card.getAvailable());
        res.setDueDate(card.getDueDate());
        res.setClosingDate(card.getClosingDate());
        res.setCreatedAt(card.getCreatedAt());
        res.setUpdatedAt(card.getUpdatedAt());
        if (card.getUser() != null) {
            res.setUser(new UserSummary(card.getUser().getId(), card.getUser().getName()));
        }
        if (card.getBank() != null) {
            res.setBank(new BankSummary(card.getBank().getId(), card.getBank().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }

    public record BankSummary(UUID id, String name) {
    }
}
