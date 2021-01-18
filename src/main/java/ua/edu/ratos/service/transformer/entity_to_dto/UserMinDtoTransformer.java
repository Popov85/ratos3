package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.dto.out.UserMinOutDto;

@Deprecated
@Component
public class UserMinDtoTransformer {

    public UserMinOutDto toDto(@NonNull final User entity) {
        return new UserMinOutDto()
                .setName(entity.getName())
                .setSurname(entity.getSurname())
                .setEmail(entity.getEmail());
    }
}
