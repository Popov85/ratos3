package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.out.UserMinOutDto;

@Component
public class UserMinDtoTransformer {

    public UserMinOutDto toDto(@NonNull final User entity) {
        UserMinOutDto user = new UserMinOutDto();
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setEmail(entity.getEmail());
        return user;
    }
}
