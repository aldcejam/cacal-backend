package br.com.minhascontas.dto.expense;

import br.com.minhascontas.model.GastoRecorrente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSaveRes {

    private UUID id;
    private String pagamento;
    private String descricao;
    private String categoria;
    private BigDecimal valor;
    private UserSummary user;
    private LocalDateTime createdAt;

    public static ExpenseSaveRes fromEntity(GastoRecorrente gasto) {
        ExpenseSaveRes res = new ExpenseSaveRes();
        res.setId(gasto.getId());
        res.setPagamento(gasto.getPagamento());
        res.setDescricao(gasto.getDescricao());
        res.setCategoria(gasto.getCategoria());
        res.setValor(gasto.getValor());
        res.setCreatedAt(gasto.getCreatedAt());
        if (gasto.getUser() != null) {
            res.setUser(new UserSummary(gasto.getUser().getId(), gasto.getUser().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }
}
