package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.out.UserOutDto;

@Component
@AllArgsConstructor
public class UserDtoTransformer {

    private final RoleDtoTransformer roleDtoTransformer;

    public UserOutDto toDto(@NonNull final User entity) {
        UserOutDto user = new UserOutDto();
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setEmail(entity.getEmail());
        user.setActive(entity.isActive());
        user.setRole(roleDtoTransformer.toDto(entity.getRoles().iterator().next()).getName()); // TODO: bad design, alas and sorry))
        return user;
    }
}
