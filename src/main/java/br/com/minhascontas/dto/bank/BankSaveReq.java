package br.com.minhascontas.dto.bank;

import br.com.minhascontas.model.Banco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankSaveReq {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Limite é obrigatório")
    @Positive(message = "Limite deve ser positivo")
    private BigDecimal limitValue;

    @NotBlank(message = "Data de vencimento é obrigatória")
    private String dueDate;

    @NotBlank(message = "Data de fechamento é obrigatória")
    private String closingDate;

    @NotBlank(message = "Cor é obrigatória")
    private String color;

    public Banco toEntity() {
        Banco banco = new Banco();
        banco.setName(this.name);
        banco.setLimitValue(this.limitValue);
        banco.setDueDate(this.dueDate);
        banco.setClosingDate(this.closingDate);
        banco.setColor(this.color);
        return banco;
    }
}
