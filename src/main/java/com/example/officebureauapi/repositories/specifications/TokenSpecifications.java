package com.example.officebureauapi.repositories.specifications;

import com.example.officebureauapi.entities.Token;
import com.example.officebureauapi.entities.Token_;
import com.example.officebureauapi.entities.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface TokenSpecifications {

    static Specification<Token> validTokensByUser(UUID userId) {
        return (root, query, builder) -> {
            return builder.and(builder.equal(root.get(Token_.USER).get(String.valueOf(User_.id)), userId),
                    builder.or(
                            builder.isFalse(root.get(Token_.EXPIRED)),
                            builder.isFalse(root.get(Token_.REVOKED))
                    ));
        };
    }
}
