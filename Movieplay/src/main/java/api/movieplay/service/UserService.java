package api.movieplay.service;

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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
