package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.dto.out.OptionsOutDto;
import ua.edu.ratos.service.transformer.StaffMinMapper;

@Deprecated
@Slf4j
@Component
@AllArgsConstructor
public class OptionsDtoTransformer {

    private final StaffMinMapper staffMinMapper;

    private final ModelMapper modelMapper;

    public OptionsOutDto toDto(@NonNull final Options entity) {
        OptionsOutDto optionsOutDto = modelMapper.map(entity, OptionsOutDto.class);
        optionsOutDto.setStaff(staffMinMapper.toDto(entity.getStaff()));
        return optionsOutDto;
    }
}
