package br.com.myaccounts.dto.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MonthSummary {
    private BigDecimal pendingToPay;
    private BigDecimal leftover;
    private List<TransactionFindRes> periodTransactions;
}
