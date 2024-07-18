package api.movieplay.model.mapper;

import api.movieplay.dto.UserDTO;
import api.movieplay.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        dto.setProfileImage(user.getProfileImage());
        dto.setRatings(user.getRatings());
        dto.setFavorited(user.getFavorited());
        dto.setRefreshToken(user.getRefreshToken());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setProfileImage(dto.getProfileImage());
        user.setRatings(dto.getRatings());
        user.setFavorited(dto.getFavorited());
        user.setRefreshToken(dto.getRefreshToken());

        return user;
    }
}
