package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.out.SchemeInfoOutDto;

@Slf4j
@RestController
@RequestMapping("/info")
@AllArgsConstructor
public class InfoController {

    private final SchemeService schemeService;

    private final SecurityUtils securityUtils;

    @Getter
    @ToString
    @AllArgsConstructor
    private static class PanelInfoDto {
        private String user;
        private String email;
        private boolean isLms;
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
