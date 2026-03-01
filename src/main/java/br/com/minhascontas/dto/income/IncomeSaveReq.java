package br.com.minhascontas.dto.income;

import br.com.minhascontas.model.Receita;
import br.com.minhascontas.model.Usuario;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class IncomeSaveReq {

    @NotNull(message = "Usuário é obrigatório")
    private UUID userId;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    @NotNull(message = "Dia de recebimento é obrigatório")
    @Min(value = 1, message = "Dia deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia deve ser entre 1 e 31")
    private Integer diaRecebimento;

    public Receita toEntity(Usuario user) {
        Receita receita = new Receita();
        receita.setUser(user);
        receita.setDescricao(this.descricao);
        receita.setCategoria(this.categoria);
        receita.setValor(this.valor);
        receita.setDiaRecebimento(this.diaRecebimento);
        return receita;
    }
}
