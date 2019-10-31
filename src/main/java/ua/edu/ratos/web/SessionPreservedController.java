package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.SessionPreservedService;
import ua.edu.ratos.service.dto.out.SessionPreservedOutDto;

@Slf4j
@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class SessionPreservedController {

    private final SessionPreservedService sessionPreservedService;

    //--------------------------------------------------User table------------------------------------------------------
    @GetMapping(value = "/sessions-preserved", produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<SessionPreservedOutDto> findAllByUserId(@PageableDefault(sort = {"whenPreserved"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        return sessionPreservedService.findAllByUserId(pageable);
    }

    //----------------------------------------------------Remove--------------------------------------------------------
    @DeleteMapping("/sessions-preserved/{key}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String key) {
        sessionPreservedService.deleteById(key);
        log.debug("Deleted preserved Session, key = {}", key);
    }
}
