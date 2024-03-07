package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.StrategyRepository;
import ua.edu.ratos.service.dto.out.StrategyOutDto;
import ua.edu.ratos.service.transformer.mapper.StrategyMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StrategyService {

    private final StrategyRepository strategyRepository;

    private final StrategyMapper strategyMapper;

    @Transactional(readOnly = true)
    public List<StrategyOutDto> findAll() {
        return strategyRepository.findAll().stream()
                .map(strategyMapper::toDto)
                .sorted(Comparator.comparing(StrategyOutDto::getStrId))
                .collect(Collectors.toList());
    }

}
