package api.movieplay.service.auth;

import api.movieplay.model.entity.AuthTokens;
import api.movieplay.model.entity.User;

public interface AuthService {

    User authenticate(String oauthToken);

    void logout(String token);

    AuthTokens refresh(String refreshToken) throws InvalidRefreshTokenException;

    String generateJwt(User user);
}
