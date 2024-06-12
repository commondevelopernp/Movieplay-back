package api.movieplay.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health/alive")
    public ResponseEntity<?> alive()
    {
        return ResponseEntity.ok(("Alive"));
    }
}
