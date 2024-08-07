package api.movieplay.controller;

import api.movieplay.dto.UserDTO;
import api.movieplay.model.entity.User;
import api.movieplay.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/{idUsuario}/{idPelicula}")
    public User addFavorite(@PathVariable Long idUsuario, @PathVariable Long idPelicula) {
        return userService.addFavorite(idUsuario, idPelicula);
    }
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDetails) {
        Optional<UserDTO> userOptional = userService.getUserById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UserDTO user = userOptional.get();
        user.setNickname(userDetails.getNickname());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setProfileImage(userDetails.getProfileImage());
        user.setRatings(userDetails.getRatings());
        UserDTO updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
