package br.com.myaccounts.repository;

import br.com.myaccounts.model.PaymentRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRelationRepository extends JpaRepository<PaymentRelation, UUID> {
}
