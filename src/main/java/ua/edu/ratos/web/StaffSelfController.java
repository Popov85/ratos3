package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.StaffSelfService;
import ua.edu.ratos.service.dto.in.EmailInDto;
import ua.edu.ratos.service.dto.in.UserMinInDto;

@Slf4j
@RestController
@RequestMapping("/lab")
public class StaffSelfController {
    
    private StaffSelfService staffSelfService;

    @Autowired
    public void setStaffSelfService(StaffSelfService staffSelfService) {
        this.staffSelfService = staffSelfService;
    }
    
    @PutMapping("/accounts/{staffId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateNameAndSurname(@PathVariable Long staffId, @RequestBody UserMinInDto dto) {
        staffSelfService.updateNameAndSurname(dto);
        log.debug("Updated Staff's name and surname, staffId = {}", staffId);
    }

    @PutMapping("/accounts/{staffId}/email")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateEmail(@PathVariable Long staffId, @RequestBody EmailInDto dto) {
        staffSelfService.updateEmail(dto);
        log.debug("Updated Staff's email, staffId = {}", staffId);
    }

    @PutMapping("/accounts/{staffId}/password")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePassword(@PathVariable Long staffId, @RequestParam char[] oldPass, @RequestParam char[] newPass) {
        staffSelfService.updatePassword(oldPass, newPass);
        log.debug("Updated Staff's password, staffId = {}", staffId);
    }
    
}
