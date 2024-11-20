package api_user.auth.service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import api_user.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.function.Function;
import java.util.Date;

@Service
public class JwtUtilService {
    
    

    public String generateToken(UserDetails userDetails, String role) {
        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TIME_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails, String role) {
        var claims = new HashMap<String, Object>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_TIME_REFRESH_VALIDATE))
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractClaim(token, Claims::getSubject).equals(userDetails.getUsername())
                && !extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(Constants.JWT_SECRET_KEY)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claimsResolver.apply(claims);
    }
    

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

}
