package com.harshDiwathe16.authentication_application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token", indexes = {
        @Index(name = "refresh_tokens_jti_idx", columnList = "jti",  unique = true),
        @Index(name = "refresh_tokens_users_jti_idx", columnList = "user_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //token id
    @Column(name = "jti", nullable = false, unique = true, updatable = false)
    private String jti;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",  nullable = false, updatable = false)
    private User user;

    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean revoked;

    private String replacedByToken;


}
