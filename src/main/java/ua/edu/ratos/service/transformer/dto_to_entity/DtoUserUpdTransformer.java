package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.in.UserUpdInDto;

@Deprecated
@Component
public class DtoUserUpdTransformer {

    @Transactional(propagation = Propagation.MANDATORY)
    public User toEntity(@NonNull final User user, @NonNull final UserUpdInDto dto) {
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setActive(dto.isActive());
        return user;
    }
}
