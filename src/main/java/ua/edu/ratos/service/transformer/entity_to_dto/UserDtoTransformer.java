package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.out.UserOutDto;

@Component
public class UserDtoTransformer {

    private RoleDtoTransformer roleDtoTransformer;

    @Autowired
    public void setRoleDtoTransformer(RoleDtoTransformer roleDtoTransformer) {
        this.roleDtoTransformer = roleDtoTransformer;
    }

    public UserOutDto toDto(@NonNull final User entity) {
        UserOutDto user = new UserOutDto();
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setEmail(entity.getEmail());
        user.setActive(entity.isActive());
        user.setRoles(roleDtoTransformer.toDto(entity.getRoles()));
        return user;
    }
}
