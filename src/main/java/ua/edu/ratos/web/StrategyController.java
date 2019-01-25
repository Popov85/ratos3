package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.StrategyService;
import ua.edu.ratos.service.dto.out.StrategyOutDto;
import java.util.Set;

@RestController
@RequestMapping("/instructor")
public class StrategyController {

    private StrategyService strategyService;

    @Autowired
    public void setStrategyService(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping(value="/strategies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<StrategyOutDto> findAll() {
        return strategyService.findAll();
    }
}
