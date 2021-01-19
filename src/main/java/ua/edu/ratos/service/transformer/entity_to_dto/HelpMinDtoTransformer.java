package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Help;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;
import ua.edu.ratos.service.transformer.ResourceMinMapper;

@Deprecated
@Slf4j
@Component
@AllArgsConstructor
public class HelpMinDtoTransformer {

    private final ResourceMinMapper resourceMinMapper;

    public HelpMinOutDto toDto(@NonNull final Help entity) {
        return new HelpMinOutDto()
                .setHelpId(entity.getHelpId())
                .setName(entity.getName())
                .setHelp(entity.getHelp())
                .setResource((entity.getResource().isPresent()) ? resourceMinMapper.toDto(entity.getResource().get()) : null);
    }
}
