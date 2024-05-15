package api.movieplay.service;
import api.movieplay.model.entity.User;

public interface AuthService {
    User authenticate(String oauthToken);
    void logout(String token);
    String refresh(String refreshToken);
	String generateJwt(User user);
}
