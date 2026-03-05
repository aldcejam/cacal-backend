package br.com.myaccounts.repository;

import br.com.myaccounts.model.Payment;
import br.com.myaccounts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOwnerUser(User ownerUser);
}
