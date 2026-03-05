package br.com.myaccounts.dto.transaction;

import br.com.myaccounts.model.enums.TransactionType;
import br.com.myaccounts.dto.auth.UserRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
public class TransactionFindRes {
    private UUID id;
    private UUID paymentId;
    private String paymentName;
    private TransactionType type;
    private String description;
    private String category;
    private BigDecimal value;
    private Boolean isPaid;
    private Map<String, Object> recurrenceDetails;
    private UserRes user;
}
