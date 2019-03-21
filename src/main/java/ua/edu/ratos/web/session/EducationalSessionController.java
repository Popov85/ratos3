package ua.edu.ratos.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.dto.out.HelpMinOutDto;
import ua.edu.ratos.service.dto.session.ComplaintInDto;
import ua.edu.ratos.service.dto.session.StarredInDto;
import ua.edu.ratos.service.session.EducationalSessionService;
import ua.edu.ratos.service.domain.SessionData;

@Slf4j
@RestController
@RequestMapping("/student/session")
public class EducationalSessionController {

    private EducationalSessionService educationalSessionService;

    @Autowired
    public void setEducationalSessionService(EducationalSessionService educationalSessionService) {
        this.educationalSessionService = educationalSessionService;
    }

    //--------------------------------------------------PRESERVE--------------------------------------------------------

    @GetMapping(value = "/preserve")
    public ResponseEntity<String> preserve(@SessionAttribute("sessionData") SessionData sessionData) {
        String key = educationalSessionService.preserve(sessionData);
        log.debug("Preserved session = {}", key);
        return ResponseEntity.ok(key);
    }

    //---------------------------------------------------AJAX-----------------------------------------------------------

    @GetMapping(value = "/questions/{questionId}/helped")
    public ResponseEntity<HelpMinOutDto> getHelp(@PathVariable Long questionId, @SessionAttribute("sessionData") SessionData sessionData) {
        HelpMinOutDto help = educationalSessionService.help(questionId, sessionData);
        log.debug("Retrieved Help for questionId = {}, help = {}", questionId, help);
        return ResponseEntity.ok(help);
    }

    @PostMapping(value = "/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void setStarred(@PathVariable Long questionId, @RequestBody StarredInDto dto, @SessionAttribute("sessionData") SessionData sessionData) {
        educationalSessionService.star(dto, sessionData);
        log.debug("Starred questionId = {}, stars = {}", questionId, dto.getStars());
    }

    @PutMapping(value = "/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void reStarred(@PathVariable Long questionId, @RequestBody StarredInDto dto, @SessionAttribute("sessionData") SessionData sessionData) {
        educationalSessionService.reStar(dto, sessionData);
        log.debug("ReStarred questionId = {}, stars = {}", questionId, dto.getStars());
    }

    @DeleteMapping(value = "/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void unStarred(@PathVariable Long questionId, @SessionAttribute("sessionData") SessionData sessionData) {
        educationalSessionService.unStar(questionId, sessionData);
        log.debug("UnStarred questionId = {}", questionId);
    }

    @PutMapping(value = "/questions/{questionId}/skipped")
    @ResponseStatus(value = HttpStatus.OK)
    public void setSkipped(@PathVariable Long questionId, @SessionAttribute("sessionData") SessionData sessionData) {
        educationalSessionService.skip(questionId, sessionData);
        log.debug("Skipped questionId = {}", questionId);
    }

    @PostMapping(value = "/questions/{questionId}/complained")
    @ResponseStatus(value = HttpStatus.OK)
    public void setComplaint(@PathVariable Long questionId, @RequestBody ComplaintInDto dto, @SessionAttribute("sessionData") SessionData sessionData) {
        educationalSessionService.complain(dto, sessionData);
        log.debug("Complained about questionId = {}, complaint = {}", questionId, dto);
    }
}
