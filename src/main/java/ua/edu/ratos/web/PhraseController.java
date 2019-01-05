package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.security.AuthenticatedStaff;
import ua.edu.ratos.service.PhraseService;
import ua.edu.ratos.service.dto.in.PhraseInDto;
import ua.edu.ratos.service.dto.out.PhraseOutDto;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor")
public class PhraseController {

    @Autowired
    private PhraseService phraseService;

    @PostMapping(value = "/phrase", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody PhraseInDto dto) {
        final Long phraseId = phraseService.save(dto);
        log.debug("Saved Phrase :: {} ", phraseId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(phraseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/phrase/{phraseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long phraseId, @Valid @RequestBody PhraseInDto dto) {
        phraseService.update(phraseId, dto);
        log.debug("Updated Phrase ID :: {} ", phraseId);
    }

    @DeleteMapping("/phrase/{phraseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long phraseId) {
        phraseService.deleteById(phraseId);
        log.debug("Deleted Phrase ID :: {}", phraseId);
    }

    /*-------------------GET-----------------*/

    @GetMapping(value = "/phrases", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PhraseOutDto> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return phraseService.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/phrases/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhraseOutDto> findAllByStaffId(Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return phraseService.findAllLastUsedByStaffId(staffId);
    }

    @GetMapping(value = "/phrases/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhraseOutDto> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Authentication auth) {
        Long staffId = ((AuthenticatedStaff) auth.getPrincipal()).getUserId();
        return phraseService.findAllLastUsedByStaffIdAndFirstLetters(staffId, starts);
    }

    @GetMapping(value = "/phrases/by-department", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhraseOutDto> findAllByDepId(Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return phraseService.findAllLastUsedByDepId(depId);
    }

    @GetMapping(value = "/phrases/by-department", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhraseOutDto> findAllByDepIdAndLettersStarts(@RequestParam String starts, Authentication auth) {
        Long depId = ((AuthenticatedStaff) auth.getPrincipal()).getDepId();
        return phraseService.findAllLastUsedByDepIdAndFirstLetters(depId, starts);
    }

}
