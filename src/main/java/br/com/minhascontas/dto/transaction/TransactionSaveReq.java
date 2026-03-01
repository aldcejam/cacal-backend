package br.com.minhascontas.dto.transaction;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Transacao;
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

    @NotNull(message = "Cartão é obrigatório")
    private UUID cardId;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotBlank(message = "Categoria é obrigatória")
    private String category;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal value;

    @NotBlank(message = "Parcelas são obrigatórias")
    private String parcels;

    @NotNull(message = "Total é obrigatório")
    @Positive(message = "Total deve ser positivo")
    private BigDecimal total;

    public Transacao toEntity(Cartao card) {
        Transacao transacao = new Transacao();
        transacao.setCard(card);
        transacao.setDescription(this.description);
        transacao.setCategory(this.category);
        transacao.setValue(this.value);
        transacao.setParcels(this.parcels);
        transacao.setTotal(this.total);
        return transacao;
    }
}
