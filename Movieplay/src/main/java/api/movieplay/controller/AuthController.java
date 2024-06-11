package api.movieplay.controller;
import api.movieplay.model.entity.User;
import api.movieplay.service.auth.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String oauthToken = request.get("oauthToken");
        try {
            User user = authService.authenticate(oauthToken);
            String jwt = authService.generateJwt(user);

            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwt);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Bad request, invalid OAuth token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            authService.logout(token);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized, invalid or missing token");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String newToken = authService.refresh(refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Bad request, invalid refresh token");
        }
    }
}
