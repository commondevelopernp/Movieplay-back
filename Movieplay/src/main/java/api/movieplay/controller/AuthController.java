package api.movieplay.controller;

import api.movieplay.model.entity.AuthTokens;
import api.movieplay.model.entity.User;
import api.movieplay.service.auth.AuthServiceImpl;
import api.movieplay.service.auth.InvalidRefreshTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String oauthToken = credentials.get("oauthToken");
        try {
            User user = authService.authenticate(oauthToken);
            String jwt = authService.generateJwt(user);

            return ResponseEntity.ok(Map.of("jwt", jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad request, invalid OAuth token");
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken != null && !refreshToken.isEmpty()) {
            try {
                AuthTokens newTokens = authService.refresh(refreshToken);
                return ResponseEntity.ok(Map.of("accessToken", newTokens.getAccessToken(), "refreshToken", newTokens.getRefreshToken()));
            } catch (InvalidRefreshTokenException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is expired or invalid");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is missing");
    }
}
