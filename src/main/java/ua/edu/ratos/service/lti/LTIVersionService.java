package ua.edu.ratos.service.lti;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.repository.lms.LTIVersionRepository;
import ua.edu.ratos.service.dto.out.LTIVersionOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.LTIVersionDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LTIVersionService {

    private final LTIVersionRepository ltiVersionRepository;

    private final LTIVersionDtoTransformer ltiVersionDtoTransformer;

    public Set<LTIVersionOutDto> findAll() {
        return ltiVersionRepository.findAll()
                .stream()
                .map(v->ltiVersionDtoTransformer.toDto(v))
                .collect(Collectors.toSet());
    }
}
