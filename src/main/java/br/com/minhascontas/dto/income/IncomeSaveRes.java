package br.com.minhascontas.dto.income;

import br.com.minhascontas.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSaveRes {

    private UUID id;
    private String descricao;
    private String categoria;
    private BigDecimal valor;
    private Integer diaRecebimento;
    private UserSummary user;
    private LocalDateTime createdAt;

    public static IncomeSaveRes fromEntity(Receita receita) {
        IncomeSaveRes res = new IncomeSaveRes();
        res.setId(receita.getId());
        res.setDescricao(receita.getDescricao());
        res.setCategoria(receita.getCategoria());
        res.setValor(receita.getValor());
        res.setDiaRecebimento(receita.getDiaRecebimento());
        res.setCreatedAt(receita.getCreatedAt());
        if (receita.getUser() != null) {
            res.setUser(new UserSummary(receita.getUser().getId(), receita.getUser().getName()));
        }
        return res;
    }

    public record UserSummary(UUID id, String name) {
    }
}
