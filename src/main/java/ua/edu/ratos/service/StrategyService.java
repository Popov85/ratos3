package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.StrategyRepository;
import ua.edu.ratos.service.dto.out.StrategyOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.StrategyDtoTransformer;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StrategyService {

    private StrategyRepository strategyRepository;

    private StrategyDtoTransformer strategyDtoTransformer;

    @Autowired
    public void setStrategyRepository(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Autowired
    public void setStrategyDtoTransformer(StrategyDtoTransformer strategyDtoTransformer) {
        this.strategyDtoTransformer = strategyDtoTransformer;
    }

    @Transactional(readOnly = true)
    public Set<StrategyOutDto> findAll() {
        return strategyRepository.findAll().stream().map(strategyDtoTransformer::toDto).collect(Collectors.toSet());
    }

}
