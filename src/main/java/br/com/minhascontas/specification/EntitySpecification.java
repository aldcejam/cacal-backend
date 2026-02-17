package br.com.minhascontas.specification;

import br.com.minhascontas.model.BaseEntity;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EntitySpecification {

    public static <T extends BaseEntity<?>> Specification<T> filterByDateRange(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (start == null || end == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("createdAt"), start, end);
        };
    }

    public static <T> Specification<T> filterByUser(String email) {
        return (root, query, criteriaBuilder) -> {
            Path<Object> userPath;
            if (root.getJavaType().getSimpleName().equals("Transacao")) {
                 userPath = root.get("card").get("user");
            } else {
                 userPath = root.get("user");
            }
            return criteriaBuilder.equal(userPath.get("email"), email);
        };
    }
}
