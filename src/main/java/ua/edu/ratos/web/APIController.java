package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.domain.entity.Language;
import ua.edu.ratos.domain.repository.LanguageRepository;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private LanguageRepository languageRepository;

    @CrossOrigin(maxAge = 100)
    @GetMapping("/first")
    public Language get2(Principal principal) {
        log.debug("Principal :: {}", principal);
        return languageRepository.findAll().get(0);
    }
}
