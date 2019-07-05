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
import ua.edu.ratos.service.session.SessionDataMap;

@Slf4j
@RestController
@RequestMapping(value = {"/student/session", "/lms/session"})
public class EducationalSessionController {

    private EducationalSessionService educationalSessionService;

    @Autowired
    public void setEducationalSessionService(EducationalSessionService educationalSessionService) {
        this.educationalSessionService = educationalSessionService;
    }

    //--------------------------------------------------PRESERVE--------------------------------------------------------

    @GetMapping(value = "/preserve/{schemeId}")
    public ResponseEntity<String> preserve(@PathVariable Long schemeId,
                                           @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        String key = educationalSessionService.preserve(sessionData);
        log.debug("Preserved session = {}", key);
        return ResponseEntity.ok(key);
    }

    //---------------------------------------------------AJAX-----------------------------------------------------------

    @GetMapping(value = "/schemes/{schemeId}/questions/{questionId}/helped")
    public ResponseEntity<HelpMinOutDto> getHelp(@PathVariable Long schemeId,
                                                 @PathVariable Long questionId,
                                                 @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        HelpMinOutDto help = educationalSessionService.help(questionId, sessionData);
        log.debug("Retrieved Help for questionId = {}, help = {}", questionId, help);
        return ResponseEntity.ok(help);
    }

    @PostMapping(value = "/schemes/{schemeId}/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void setStarred(@PathVariable Long schemeId,
                           @PathVariable Long questionId,
                           @RequestBody StarredInDto dto,
                           @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        educationalSessionService.star(dto, sessionData);
        log.debug("Starred questionId = {}, stars = {}", questionId, dto.getStars());
    }

    @PutMapping(value = "/schemes/{schemeId}/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void reStarred(@PathVariable Long schemeId,
                          @PathVariable Long questionId,
                          @RequestBody StarredInDto dto,
                          @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        educationalSessionService.reStar(dto, sessionData);
        log.debug("ReStarred questionId = {}, stars = {}", questionId, dto.getStars());
    }

    @DeleteMapping(value = "/schemes/{schemeId}/questions/{questionId}/starred")
    @ResponseStatus(value = HttpStatus.OK)
    public void unStarred(@PathVariable Long schemeId,
                          @PathVariable Long questionId,
                          @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        educationalSessionService.unStar(questionId, sessionData);
        log.debug("UnStarred questionId = {}", questionId);
    }

    @PutMapping(value = "/schemes/{schemeId}/questions/{questionId}/skipped")
    @ResponseStatus(value = HttpStatus.OK)
    public void setSkipped(@PathVariable Long schemeId,
                           @PathVariable Long questionId,
                           @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        educationalSessionService.skip(questionId, sessionData);
        log.debug("Skipped questionId = {}", questionId);
    }

    @PostMapping(value = "/schemes/{schemeId}/questions/{questionId}/complained")
    @ResponseStatus(value = HttpStatus.OK)
    public void setComplaint(@PathVariable Long schemeId,
                             @PathVariable Long questionId,
                             @RequestBody ComplaintInDto dto,
                             @SessionAttribute("sessionDataMap") SessionDataMap sessionDataMap) {
        SessionData sessionData = sessionDataMap.getOrElseThrow(schemeId);
        educationalSessionService.complain(dto, sessionData);
        log.debug("Complained about questionId = {}, complaint = {}", questionId, dto);
    }
}
