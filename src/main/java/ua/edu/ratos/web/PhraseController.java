package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.edu.ratos.domain.entity.Phrase;
import ua.edu.ratos.service.PhraseService;
import ua.edu.ratos.service.dto.entity.PhraseInDto;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructor/phrases")
public class PhraseController {

    @Autowired
    private PhraseService phraseService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody PhraseInDto dto) {
        final Long phraseId = phraseService.save(dto);
        log.debug("Saved Phrase :: {} ", phraseId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(phraseId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{phraseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long phraseId, @Valid @RequestBody PhraseInDto dto) {
        phraseService.update(phraseId, dto);
        log.debug("Updated Phrase ID :: {} ", phraseId);
    }

    @DeleteMapping("/{phraseId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long phraseId) {
        phraseService.deleteById(phraseId);
        log.debug("Deleted Phrase ID :: {}", phraseId);
    }

    /*-------------------GET-----------------*/

    @GetMapping(value = "/by-staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Phrase> findAllByStaffId(Principal principal) {
        return phraseService.findAllLastUsedByStaffId(1L);
    }

    @GetMapping(value = "/by-staff", params = "starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Phrase> findAllByStaffIdAndLettersStarts(@RequestParam String starts, Principal principal) {
        return phraseService.findAllLastUsedByStaffIdAndFirstLetters(1L, starts);
    }

}
