package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Role;
import ua.edu.ratos.service.dto.out.RoleOutDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleDtoTransformer {

    public RoleOutDto toDto(@NonNull final Role entity) {
        return new RoleOutDto()
                .setRoleId(entity.getRoleId())
                .setName(entity.getName());
    }

    public Set<RoleOutDto> toDto(@NonNull final Set<Role> entities) {
        return entities
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
