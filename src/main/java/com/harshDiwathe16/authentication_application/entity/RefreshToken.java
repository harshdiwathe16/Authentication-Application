package com.harshDiwathe16.authentication_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Instant createdAt;

    private Instant expiresAt;

    private Boolean revoked;

    private String replacedByToken;


}
