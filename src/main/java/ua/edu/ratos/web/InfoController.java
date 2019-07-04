package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.SchemeService;
import ua.edu.ratos.service.dto.out.SchemeInfoOutDto;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {

    private SchemeService schemeService;

    @Autowired
    public void setSchemeService(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    //----------------------------------------------------Scheme Info--------------------------------------------------

    @GetMapping(value = "/schemes/{schemeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchemeInfoOutDto> findOneForInfo(@PathVariable Long schemeId){
        SchemeInfoOutDto info = schemeService.findByIdForInfo(schemeId);
        log.debug("SchemeInfo = {}", info);
        return ResponseEntity.ok(info);
    }
}
