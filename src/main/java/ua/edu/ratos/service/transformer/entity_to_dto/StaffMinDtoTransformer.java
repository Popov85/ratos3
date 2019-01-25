package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;

@Slf4j
@Component
public class StaffMinDtoTransformer {

    public StaffMinOutDto toDto(@NonNull final Staff entity) {
        return new StaffMinOutDto()
                .setStaffId(entity.getStaffId())
                .setName(entity.getUser().getName())
                .setSurname(entity.getUser().getSurname())
                .setPosition(entity.getPosition().getName());
    }
}
