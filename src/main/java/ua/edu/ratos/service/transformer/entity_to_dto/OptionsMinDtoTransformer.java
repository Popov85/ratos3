package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.dto.out.OptionsMinOutDto;

@Deprecated
@Slf4j
@Component
public class OptionsMinDtoTransformer {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OptionsMinOutDto toDto(@NonNull final Options entity) {
        return modelMapper.map(entity, OptionsMinOutDto.class);
    }
}
