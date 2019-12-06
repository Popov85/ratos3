package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.*;
import ua.edu.ratos.service.dto.in.PositionInDto;

@Component
@AllArgsConstructor
public class DtoPositionTransformer {

    private final ModelMapper modelMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public Position toEntity(@NonNull final PositionInDto dto) {
        Position position = modelMapper.map(dto, Position.class);
        return position;
    }
}
