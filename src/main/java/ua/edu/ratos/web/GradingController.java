package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.dto.out.GradingOutDto;
import ua.edu.ratos.service.session.GradingService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class GradingController {

    private final GradingService accessService;

    // For dep .staff. to get a set of available accesses
    @GetMapping(value = "/department/gradings-dropdown/all-gradings-by-ratos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GradingOutDto> findAllGradingsForDropDown() {
        return accessService.findAllGradingsForDropDown();
    }
}
