package com.harshDiwathe16.authentication_application.security;

import com.harshDiwathe16.authentication_application.helpers.UserHelper;
import com.harshDiwathe16.authentication_application.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                // FIXED LOGIC
                if (!jwtService.isAccessToken(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Claims payload =
                        jwtService.parseToken(token).getPayload();

                UUID userUUID =
                        UserHelper.parseUUID(payload.getSubject());

                userRepository.findById(userUUID)
                        .ifPresent(user -> {

                            if (user.isEnabled()) {

                                List<GrantedAuthority> authorities =
                                        user.getRoles() == null ? List.of()
                                                : user.getRoles().stream()
                                                .map(role ->
                                                        new SimpleGrantedAuthority(
                                                                role.getRoleName()))
                                                .collect(Collectors.toList());

                                UsernamePasswordAuthenticationToken auth =
                                        new UsernamePasswordAuthenticationToken(
                                                user.getEmail(),
                                                null,
                                                authorities
                                        );

                                auth.setDetails(
                                        new WebAuthenticationDetailsSource()
                                                .buildDetails(request)
                                );

                                if (SecurityContextHolder.getContext()
                                        .getAuthentication() == null) {
                                    SecurityContextHolder.getContext()
                                            .setAuthentication(auth);
                                }
                            }
                        });

            }
            catch (ExpiredJwtException e)
            {
                request.setAttribute("error", "Token Expired");
            } catch (MalformedJwtException e)
            {
                request.setAttribute("error", "Invalid Token");
//                e.printStackTrace();
            } catch (JwtException e)
            {
                request.setAttribute("error", "Invalid Token");
//                e.printStackTrace();
            }
            catch (Exception e)
            {
                request.setAttribute("error", "Invalid Token");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("api/v1/auth");
    }
}