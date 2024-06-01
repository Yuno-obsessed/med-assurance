package sanity.nil.medassurance.middleware;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sanity.nil.medassurance.config.JwtUtils;
import sanity.nil.medassurance.db.model.RoleModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/v1/med-ass/login") ||
                request.getRequestURI().equals("/api/v1/med-ass/register") ||
                request.getRequestURI().startsWith("/swagger-ui/") ||
                request.getRequestURI().startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = extractJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                final String email = jwtUtils.getEmailFromJwtToken(jwt);
                final List<String> roleStrings = jwtUtils.getRolesFromJwtToken(jwt);
                List<GrantedAuthority> authorities = roleStrings.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                final UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("No access token found in a request");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

}
