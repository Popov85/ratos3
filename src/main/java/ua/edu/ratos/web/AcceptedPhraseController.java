package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.answer.AcceptedPhrase;
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
        log.debug("Saved phrase :: {} ", phraseId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(phraseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{phraseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long phraseId, @Valid @RequestBody AcceptedPhraseInDto dto) {
        acceptedPhraseService.update(phraseId, dto);
        log.debug("Updated phrase ID :: {} ", phraseId);
    }

    @DeleteMapping("/{phraseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long phraseId) {
        acceptedPhraseService.deleteById(phraseId);
        log.debug("AcceptedPhrase to delete ID :: {}", phraseId);
    }

/*-------------------GET-----------------*/

    @GetMapping("/by-staff")
    public List<AcceptedPhrase> findAllByStaffId(Principal principal) {
        final List<AcceptedPhrase> result = acceptedPhraseService.findAllLastUsedByStaffId(1L);
        log.debug("AcceptedPhrases by staff (size) :: {}", result.size());
        return result;
    }

    @GetMapping(value = "/by-staff", params = "starts")
    public List<AcceptedPhrase> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Principal principal) {
        final List<AcceptedPhrase> result = acceptedPhraseService.findAllLastUsedByStaffIdAndFirstLetters(1L, starts);
        log.debug("AcceptedPhrases by staff and letters (size) :: {}", result.size());
        return result;
    }

}
