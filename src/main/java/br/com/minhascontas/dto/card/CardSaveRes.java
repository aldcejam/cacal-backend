package br.com.minhascontas.dto.card;

import br.com.minhascontas.model.Cartao;
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
public class CardSaveRes {

    private UUID id;
    private String lastDigits;
    private BigDecimal limitValue;
    private BigDecimal available;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private UserSummary user;
    private BankSummary bank;
    private LocalDateTime createdAt;

    public static CardSaveRes fromEntity(Cartao cartao) {
        CardSaveRes res = new CardSaveRes();
        res.setId(cartao.getId());
        res.setLastDigits(cartao.getLastDigits());
        res.setLimitValue(cartao.getLimitValue());
        res.setAvailable(cartao.getAvailable());
        res.setDueDate(cartao.getDueDate());
        res.setClosingDate(cartao.getClosingDate());
        res.setCreatedAt(cartao.getCreatedAt());
        if (cartao.getUser() != null) {
            res.setUser(new UserSummary(cartao.getUser().getId(), cartao.getUser().getName()));
        }
        if (cartao.getBank() != null) {
            res.setBank(new BankSummary(cartao.getBank().getId(), cartao.getBank().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }

    public record BankSummary(UUID id, String name) {
    }
}
