package api.movieplay.service.auth;

import api.movieplay.model.entity.User;

public interface AuthService {

    User authenticate(String oauthToken);

    void logout(String token);

    String refresh(String refreshToken) throws InvalidRefreshTokenException;

    String generateJwt(User user);
}
