package br.com.myaccounts.repository;

import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.model.User;
import br.com.myaccounts.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.user = :user " +
            "AND (cast(:startDate as date) IS NULL OR cast(function('jsonb_extract_path_text', t.recurrenceDetails, 'date') as date) >= :startDate) "
            +
            "AND (cast(:endDate as date) IS NULL OR cast(function('jsonb_extract_path_text', t.recurrenceDetails, 'date') as date) <= :endDate) "
            +
            "AND (:type IS NULL OR t.type = :type)")
    List<Transaction> findFilteredTransactions(User user, LocalDate startDate, LocalDate endDate, TransactionType type);

    List<Transaction> findByUser(User user);
}
