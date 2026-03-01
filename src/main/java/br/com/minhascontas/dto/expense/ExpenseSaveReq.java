package br.com.minhascontas.dto.expense;

import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.model.Usuario;
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
public class ExpenseSaveReq {

    @NotNull(message = "Usuário é obrigatório")
    private UUID userId;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    private String pagamento;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    public GastoRecorrente toEntity(Usuario user) {
        GastoRecorrente gasto = new GastoRecorrente();
        gasto.setUser(user);
        gasto.setPagamento(this.pagamento);
        gasto.setDescricao(this.descricao);
        gasto.setCategoria(this.categoria);
        gasto.setValor(this.valor);
        return gasto;
    }
}
