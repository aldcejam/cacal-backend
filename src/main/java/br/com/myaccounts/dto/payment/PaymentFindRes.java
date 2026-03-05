package br.com.myaccounts.dto.payment;

import br.com.myaccounts.model.enums.PaymentMethodType;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class PaymentFindRes {
    private UUID id;
    private String name;
    private PaymentMethodType type;

    private UUID bankId;
    private String personName;

    private Map<String, Object> details;
}
