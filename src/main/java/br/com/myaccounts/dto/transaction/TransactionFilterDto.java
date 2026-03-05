package br.com.myaccounts.dto.transaction;

import br.com.myaccounts.model.enums.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionFilterDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private TransactionType type;
}
