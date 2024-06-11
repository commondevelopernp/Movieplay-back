package api.movieplay.service.auth;

import api.movieplay.config.JwtConfig;
import api.movieplay.model.dao.UserRepository;
import api.movieplay.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfig jwtConfig;

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

        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
        }

        return user;
    }

    @Override
    public void logout(String token) {
        // Lógica para invalidar el JWT (por ejemplo, almacenar en una lista negra)
    }

    @Override
    public String refresh(String refreshToken) {
        // Lógica para refrescar el token y generar uno nuevo
        return "newJwtToken";
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
}
