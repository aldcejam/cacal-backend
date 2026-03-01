package br.com.minhascontas.dto.card;

import br.com.minhascontas.model.Banco;
import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Usuario;
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

    @NotBlank(message = "Últimos dígitos são obrigatórios")
    private String lastDigits;

    @NotNull(message = "Limite é obrigatório")
    @Positive(message = "Limite deve ser positivo")
    private BigDecimal limitValue;

    @NotNull(message = "Disponível é obrigatório")
    private BigDecimal available;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dueDate;

    @NotNull(message = "Data de fechamento é obrigatória")
    private LocalDate closingDate;

    @NotNull(message = "Usuário é obrigatório")
    private UUID userId;

    @NotNull(message = "Banco é obrigatório")
    private UUID bankId;

    public Cartao toEntity(Usuario user, Banco bank) {
        Cartao cartao = new Cartao();
        cartao.setLastDigits(this.lastDigits);
        cartao.setLimitValue(this.limitValue);
        cartao.setAvailable(this.available);
        cartao.setDueDate(this.dueDate);
        cartao.setClosingDate(this.closingDate);
        cartao.setUser(user);
        cartao.setBank(bank);
        return cartao;
    }
}
