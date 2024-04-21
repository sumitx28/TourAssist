package com.group15.tourassist.entity;

import com.group15.tourassist.core.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a token entity used to store JWT stateless authentication tokens associated with a user.
 *
 * <p>
 * This class is annotated with Lombok annotations to generate common boilerplate code.
 * - {@code @Data}: Generates getters, setters, toString, equals, and hashCode methods.
 * - {@code @Builder}: Provides a builder pattern for easy object creation.
 * - {@code @NoArgsConstructor}: Generates a default constructor with no parameters.
 * - {@code @AllArgsConstructor}: Generates a constructor with all class fields as parameters.
 * </p>
 *
 * @author snehitroda
 * @implNote Token entity is used to store the JWT stateless auth tokens associated with a user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean isRevoked;

    private boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")

    private AppUser appUser;
}
