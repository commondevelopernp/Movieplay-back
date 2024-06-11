package api.movieplay.service.auth;

public class InvalidRefreshTokenException extends Exception {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
