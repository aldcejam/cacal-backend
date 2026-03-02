package br.com.myaccounts.dto.transaction;
import br.com.myaccounts.model.User;

import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.Transaction;
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
public class TransactionSaveReq {

    @NotNull(message = "Card is required")
    private UUID cardId;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private BigDecimal value;

    @NotBlank(message = "Parcels are required")
    private String parcels;

    @NotNull(message = "Total is required")
    @Positive(message = "Total deve ser positivo")
    private BigDecimal total;

    public Transaction toEntity(Card card) {
        Transaction transacao = new Transaction();
        transacao.setCard(card);
        transacao.setDescription(this.description);
        transacao.setCategory(this.category);
        transacao.setValue(this.value);
        transacao.setParcels(this.parcels);
        transacao.setTotal(this.total);
        return transacao;
    }
}
