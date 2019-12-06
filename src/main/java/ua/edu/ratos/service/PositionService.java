package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Position;
import ua.edu.ratos.dao.repository.PositionRepository;
import ua.edu.ratos.service.dto.in.PositionInDto;
import ua.edu.ratos.service.dto.out.PositionOutDto;
import ua.edu.ratos.service.transformer.dto_to_entity.DtoPositionTransformer;
import ua.edu.ratos.service.transformer.entity_to_dto.PositionDtoTransformer;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PositionService {

    private static final String POSITION_NOT_FOUND = "Requested position is not found, posId = ";

    private final PositionRepository positionRepository;

    private final DtoPositionTransformer dtoPositionTransformer;

    private final PositionDtoTransformer positionDtoTransformer;

    @Transactional
    public Long save(@NonNull final PositionInDto dto) {
        Position position = dtoPositionTransformer.toEntity(dto);
        return positionRepository.save(position).getPosId();
    }

    @Transactional
    public void updateName(@NonNull final Long posId, @NonNull final String name) {
        positionRepository.findById(posId)
                .orElseThrow(() -> new EntityNotFoundException(POSITION_NOT_FOUND + posId))
                .setName(name);
    }

    @Transactional
    public void deleteById(@NonNull final Long posId) {
        positionRepository.findById(posId)
                .orElseThrow(() -> new EntityNotFoundException(POSITION_NOT_FOUND + posId))
                .setDeleted(true);
    }

    public Set<PositionOutDto> findAll() {
        return positionRepository.findAll()
                .stream()
                .map(p->positionDtoTransformer.toDto(p))
                .collect(Collectors.toSet());
    }
}
