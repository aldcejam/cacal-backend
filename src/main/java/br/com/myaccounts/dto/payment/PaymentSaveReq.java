package br.com.myaccounts.dto.payment;

import br.com.myaccounts.model.enums.PaymentMethodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class PaymentSaveReq {
    @NotBlank
    private String name;

    @NotNull
    private PaymentMethodType type;

    private UUID bankId;
    private String personName;

    private Map<String, Object> details;
}
