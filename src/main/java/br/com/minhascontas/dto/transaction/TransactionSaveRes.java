package br.com.minhascontas.dto.transaction;

import br.com.minhascontas.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSaveRes {

    private UUID id;
    private String description;
    private String category;
    private BigDecimal value;
    private String parcels;
    private BigDecimal total;
    private CardSummary card;
    private LocalDateTime createdAt;

    public static TransactionSaveRes fromEntity(Transacao transacao) {
        TransactionSaveRes res = new TransactionSaveRes();
        res.setId(transacao.getId());
        res.setDescription(transacao.getDescription());
        res.setCategory(transacao.getCategory());
        res.setValue(transacao.getValue());
        res.setParcels(transacao.getParcels());
        res.setTotal(transacao.getTotal());
        res.setCreatedAt(transacao.getCreatedAt());
        if (transacao.getCard() != null) {
            res.setCard(new CardSummary(transacao.getCard().getId(), transacao.getCard().getLastDigits()));
        }
        return res;
    }

    public record CardSummary(UUID id, String lastDigits) {
    }
}
