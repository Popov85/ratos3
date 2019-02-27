package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.service.domain.UserDomain;

@Component
public class UserDomainTransformer {

    public UserDomain toDomain(@NonNull final User entity) {
        return new UserDomain()
                .setUserId(entity.getUserId())
                .setName(entity.getName())
                .setSurname(entity.getSurname());
    }
}
