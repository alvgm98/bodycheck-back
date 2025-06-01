package bodycheck_back.bodycheck.auth.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

   private final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

   public String getToken(UserDetails user) {
      return getToken(new HashMap<>(), user);
   }

   private String getToken(Map<String, Object> extraClaims, UserDetails user) {
      return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(6)))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
   }

   public String getUsernameFromToken(String token) {
      return getC1aim(token, Claims::getSubject);
   }

   public boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = getUsernameFromToken(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   private Date getExpiration(String token) {
      return getC1aim(token, Claims::getExpiration);
   }

   private boolean isTokenExpired(String token) {
      return getExpiration(token).before(new Date());
   }

   private Claims getAllClaims(String token) {
      return Jwts
            .parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
   }

   public <T> T getC1aim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = getAllClaims(token);

      return claimsResolver.apply(claims);
   }

}
