package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.StudentSelfService;
import ua.edu.ratos.service.dto.in.EmailInDto;
import ua.edu.ratos.service.dto.in.UserMinInDto;

@Slf4j
@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentSelfController {

    private final StudentSelfService studentSelfService;

    @PutMapping("/accounts/{studentId}/name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateNameAndSurname(@PathVariable Long studentId, @RequestBody UserMinInDto dto) {
        studentSelfService.updateNameAndSurname(dto);
        log.debug("Updated Student's name and surname, studentId = {}", studentId);
    }

    @PutMapping("/accounts/{studentId}/email")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateEmail(@PathVariable Long studentId, @RequestBody EmailInDto dto) {
        studentSelfService.updateEmail(dto);
        log.debug("Updated Student's email, studentId = {}", studentId);
    }

    @PutMapping("/accounts/{studentId}/password")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePassword(@PathVariable Long studentId, @RequestParam char[] oldPass, @RequestParam char[] newPass) {
        studentSelfService.updatePassword(oldPass, newPass);
        log.debug("Updated Student's password, studentId = {}", studentId);
    }

}
