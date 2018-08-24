package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.AcceptedPhraseService;
import ua.edu.ratos.service.dto.entity.AcceptedPhraseInDto;

@Slf4j
@RestController
public class AcceptedPhraseController {
    @Autowired
    private AcceptedPhraseService acceptedPhraseService;

    @PostMapping("/acceptedPhrase")
    public Long save(@Validated({AcceptedPhraseInDto.class}) @RequestBody AcceptedPhraseInDto dto) {
        log.debug("AcceptedPhrase dto :: {} ", dto);
      /*  final Long generatedId = acceptedPhraseService.save(dto);
        log.debug("Saved phrase ID = {} ", generatedId);*/
        return 1L;
    }

    @PutMapping("/acceptedPhrase")
    public Long update(@Validated({AcceptedPhraseInDto.Update.class}) @RequestBody AcceptedPhraseInDto dto) {
        log.debug("AcceptedPhrase dto :: {} ", dto);
      /* acceptedPhraseService.update(dto);
        log.debug("Updated phrase ID = {} ", dto.getPhraseId());*/
        return 1L;
    }

    @DeleteMapping("/acceptedPhrase/{phraseId}")
    public void delete(@PathVariable Long phraseId) {
        log.debug("AcceptedPhrase to delete ID :: {}", phraseId);
    }
}
