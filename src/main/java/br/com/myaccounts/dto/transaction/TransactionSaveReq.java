package br.com.myaccounts.dto.transaction;

import br.com.myaccounts.model.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
public class TransactionSaveReq {

    private UUID paymentId;

    @NotNull
    private TransactionType type;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal value;

    @NotNull
    private Boolean isPaid;

    private Map<String, Object> recurrenceDetails;
}
