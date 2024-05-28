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

    @Value("${application.security.jwt.expirationDays}")
    private int jwtExpirationDays;

    public String generateAccessToken(String userID, List<String> roles) {
        String rolesString = String.join(",", roles);
        return Jwts.builder()
                .setSubject(userID)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(jwtExpirationMin).atZone(ZoneId.systemDefault()).toInstant()))
                .claim("roles", rolesString)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

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

    public String generateRefreshToken(String userID, List<String> roles) {
        String rolesString = String.join(",", roles);
        return Jwts.builder()
                .setSubject(userID)
                .setAudience(UUID.randomUUID().toString().substring(0, 15))
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(LocalDateTime.now().plusDays(jwtExpirationDays).atZone(ZoneId.systemDefault()).toInstant()))
                .claim("roles", rolesString)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserIDFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getAudience();
    }

    public Date getExpiryDateFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    public List<String> getRolesFromJwtToken(String token) {
        String rolesString = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("roles", String.class);
        return Arrays.stream(rolesString.split(",")).toList();
    }

    public boolean isTokenExpired(String token) {
        boolean expired;
        try {
            expired = getExpiryDateFromJwtToken(token).before(new Date());
        } catch (Exception e) {
            expired = true;
        }
        return expired;
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
