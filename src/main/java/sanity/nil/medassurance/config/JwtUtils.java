package sanity.nil.medassurance.config;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {

    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.expirationMin}")
    private int jwtExpirationMin;

    public String generateAccessToken(String userID, String email, List<String> roles) {
        String rolesString = String.join(",", roles);
        return Jwts.builder()
                .setSubject(userID)
                .setAudience(email)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(jwtExpirationMin).atZone(ZoneId.systemDefault()).toInstant()))
                .claim("roles", rolesString)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getAudience();
    }

    public List<String> getRolesFromJwtToken(String token) {
        String rolesString = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("roles", String.class);
        return Arrays.stream(rolesString.split(",")).toList();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
