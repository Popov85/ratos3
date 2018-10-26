package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.AcceptedPhrase;
import ua.edu.ratos.service.AcceptedPhraseService;
import ua.edu.ratos.service.dto.entity.AcceptedPhraseInDto;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor/accepted-phrases")
public class AcceptedPhraseController {

    @Autowired
    private AcceptedPhraseService acceptedPhraseService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody AcceptedPhraseInDto dto) {
        final Long phraseId = acceptedPhraseService.save(dto);
        log.debug("Saved AcceptedPhrase :: {} ", phraseId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(phraseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{phraseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long phraseId, @Valid @RequestBody AcceptedPhraseInDto dto) {
        acceptedPhraseService.update(phraseId, dto);
        log.debug("Updated AcceptedPhrase ID :: {} ", phraseId);
    }

    @DeleteMapping("/{phraseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long phraseId) {
        acceptedPhraseService.deleteById(phraseId);
        log.debug("Deleted AcceptedPhrase ID :: {}", phraseId);
    }

    /*-------------------GET-----------------*/

    @GetMapping(value = "/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AcceptedPhrase> findAllByStaffId(Principal principal) {
        return acceptedPhraseService.findAllLastUsedByStaffId(1L);
    }

    @GetMapping(value = "/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AcceptedPhrase> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Principal principal) {
        return acceptedPhraseService.findAllLastUsedByStaffIdAndFirstLetters(1L, starts);
    }

}
