package api.movieplay.service.auth;

import api.movieplay.model.dao.UserRepository;
import api.movieplay.model.entity.AuthTokens;
import api.movieplay.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import api.movieplay.config.JwtConfig;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private BlacklistedTokenService blacklistedTokenService;

    @Override
    public User authenticate(String oauthToken) {
        // Verificar el token OAuth con Google
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + oauthToken;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(userInfoEndpointUri, Map.class);

        if (response == null || response.containsKey("error")) {
            throw new RuntimeException("Invalid OAuth token");
        }

        // Extraer detalles del usuario de la respuesta
        String email = (String) response.get("email");
        String firstName = (String) response.get("given_name");
        String lastName = (String) response.get("family_name");
        String nickname = (String) response.get("name");
        String profilepic = (String) response.get("picture");

        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setProfileImage(profilepic);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNickname(nickname);
        }

        // Guardar o actualizar el usuario en la base de datos
        var currentUser = userRepository.save(user);

        // Generar nuevo JWT y Refresh Token
        String newAccessToken = generateJwt(currentUser);
        String newRefreshToken = generateRefreshToken(currentUser);

        // Almacenar el Refresh Token en el usuario
        user.setRefreshToken(newRefreshToken);

        // Devolver el usuario junto con los tokens
        return user;
    }

    @Override
    public void logout(String token) {
        // Invalidar el JWT agregándolo a la lista negra
        blacklistedTokenService.blacklistToken(token);
    }

    @Override
    public AuthTokens refresh(String refreshToken) throws InvalidRefreshTokenException {
        // Validar el refresh token
        if (blacklistedTokenService.isTokenBlacklisted(refreshToken)) {
            throw new InvalidRefreshTokenException("Refresh token is blacklisted");
        }

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes()) // Obtener la clave secreta como un array de bytes
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException("Refresh token is invalid");
        }

        // Verificar la expiración del refresh token
        if (claims.getExpiration().before(new Date())) {
            throw new InvalidRefreshTokenException("Refresh token is expired");
        }

        // Obtener el usuario a partir de las claims del refresh token
        String userId = claims.getSubject();
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new InvalidRefreshTokenException("User not found"));

        // Verificar si el refresh token enviado coincide con el almacenado en el usuario
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidRefreshTokenException("Refresh token does not match");
        }

        // Generar un nuevo JWT y Refresh Token
        String newAccessToken = generateJwt(user);
        String newRefreshToken = generateRefreshToken(user);

        // Actualizar el refresh token en el usuario
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        // Devolver ambos tokens
        return new AuthTokens(newAccessToken, newRefreshToken);
    }

    @Override
    public String generateJwt(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }
}
