package io.handsone.tasktracker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  private final SecretKey key;

  private final Long tokenExpirationSecond;

  private final String SPLIT_CHARACTER = ";";

  public JwtUtil(
      @Value("${spring.jwt.secret}") String secret,
      @Value("${spring.jwt.expiration}") Long tokenExpirationSecond) {
    this.key = new SecretKeySpec(
        secret.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
    this.tokenExpirationSecond = tokenExpirationSecond;
    log.debug(secret);
    log.debug("tokenExpirationSecond = {}", tokenExpirationSecond);
  }

  public String creatToken(String username) {
    ZonedDateTime now = ZonedDateTime.now();
    ZonedDateTime expiration = now.plusSeconds(tokenExpirationSecond);

    return Jwts.builder()
        .claims(makeClaims(username))
        .issuedAt(Date.from(now.toInstant()))
        .expiration(Date.from(expiration.toInstant()))
        .signWith(key)
        .compact();
  }

  private Claims makeClaims(String username) {
    return Jwts.claims()
        .add("username", username)
        .build();
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> jwsClaims = Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException exception) {
      log.debug(exception.getMessage());
    } catch (Exception exception) {
      log.debug("Expired JWT token. message: {}", exception.getMessage());
    }
    return false;
  }

  public String getUsername(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("username", String.class);
  }

  public Boolean isExpired(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getExpiration().before(new Date());
  }
}
