package finances_practice.gmejia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Notacion @Service corresponde a logica de negocio
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //Generamos el token
    //Un claim es un dato guardado dentro del token
    public String generateToken(String username, Long userId, Integer roleId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role_id", roleId);
        return createToken(claims, username);
    }

    //Creamos el Token
    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims) //Ingresa los datos puestos anteriormente o sea el user y el role
                .setSubject(subject) //Ingresa el correo para el token
                .setIssuedAt(new Date(System.currentTimeMillis())) //Genera una fecha de creacion del token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) //Genera en cuanto tiempo expira
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //Firma del token
                .compact(); //Lo envia como un string
    }

    //Decodifica la clave
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Métodos auxiliares para JJWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer Username (Email)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Validar Token
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Saber cuanto tiempo dura el token
    public long getExpirationTime() {
        return jwtExpiration;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
