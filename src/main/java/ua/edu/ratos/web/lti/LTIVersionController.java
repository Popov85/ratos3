package ua.edu.ratos.web.lti;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.dto.out.LTIVersionOutDto;
import ua.edu.ratos.service.lti.LTIVersionService;

import java.util.Set;

@RestController
@AllArgsConstructor
public class LTIVersionController {

    private final LTIVersionService ltiVersionService;

    // For GLOBAL-ADMIN/ORG-ADMIN, etc. to support creating LMS-es
    @GetMapping(value = "/org-admin/lti-versions-dropdown/all-lti-versions-by-ratos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<LTIVersionOutDto> findAll() {
        return ltiVersionService.findAll();
    }
}
