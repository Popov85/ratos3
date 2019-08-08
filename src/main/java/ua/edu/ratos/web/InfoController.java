package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.out.SchemeInfoOutDto;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {

    @Getter
    @ToString
    @AllArgsConstructor
    private static class PanelInfoDto {
        private String user;
        private String email;
        private boolean isLms;
    }

    private SchemeService schemeService;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSchemeService(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    //---------------------------------------------------Panel Info---------------------------------------------------
    @GetMapping(value = "/panel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PanelInfoDto> findOutContext(){
        boolean isLtiUser = securityUtils.isLtiUser();
        String email = securityUtils.getAuthUsername();
        PanelInfoDto dto = new PanelInfoDto(email.split("@")[0], email, isLtiUser);
        log.debug("Panel info = {}", dto);
        return ResponseEntity.ok(dto);
    }

    //----------------------------------------------------Scheme Info---------------------------------------------------

    @GetMapping(value = "/schemes/{schemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeInfoOutDto> findSchemeForInfo(@PathVariable Long schemeId){
        SchemeInfoOutDto info = schemeService.findByIdForInfo(schemeId);
        log.debug("SchemeInfo = {}", info);
        return ResponseEntity.ok(info);
    }
}
