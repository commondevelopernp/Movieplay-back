package api.movieplay.service.user;

import api.movieplay.dto.UserDTO;
import api.movieplay.model.mapper.UserMapper;
import api.movieplay.model.entity.User;
import api.movieplay.model.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            
            // Actualiza solo los campos que se han modificado
            if (userDTO.getNickname() != null) {
                existingUser.setNickname(userDTO.getNickname());
            }
            if (userDTO.getFirstName() != null) {
                existingUser.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null) {
                existingUser.setLastName(userDTO.getLastName());
            }
            if (userDTO.getRatings() != null) {
                existingUser.setRatings(userDTO.getRatings());
            }
            if (userDTO.getFavorited() != null) {
                existingUser.setFavorited(userDTO.getFavorited());
            }

            userRepository.save(existingUser); // Guarda el usuario actualizado en la base de datos
            return userMapper.toDTO(existingUser); // Mapea la entidad actualizada a un DTO y lo devuelve
        } else {
            // Manejo de la situación en la que el usuario con el ID dado no existe
            return null; // O lanzar una excepción
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Asegúrate de que este método existe
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}

