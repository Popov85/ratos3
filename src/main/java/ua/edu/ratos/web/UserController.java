package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.ratos.service.UserService;
import ua.edu.ratos.service.dto.in.EmailInDto;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/user/update-name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@RequestParam String name) {
        userService.updateName(name);
        log.debug("Updated User's name, new name = {}", name);
    }

    @PutMapping(value = "/dep-admin/users/{userId}/update-name")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateName(@PathVariable Long userId, @RequestParam String name) {
        userService.updateName(userId, name);
        log.debug("Updated User's name, userId = {}, new name = {}", userId, name);
    }

    @PutMapping(value = "/user/update-surname")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSurname(@RequestParam String surname) {
        userService.updateSurname(surname);
        log.debug("Updated User's surname, new surname = {}", surname);
    }

    @PutMapping(value = "/dep-admin/users/{userId}/update-surname")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSurname(@PathVariable Long userId, @RequestParam String surname) {
        userService.updateSurname(userId, surname);
        log.debug("Updated User's surname, userId = {}, new surname = {}", userId, surname);
    }

    @PutMapping(value = "/user/update-password")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePassword(@RequestParam char[] password) {
        userService.updatePassword(password);
        log.debug("Updated User's password, new password = {}", password);
    }

    @PutMapping(value = "/dep-admin/users/{userId}/update-password")
    @ResponseStatus(value = HttpStatus.OK)
    public void updatePassword(@PathVariable Long userId, @RequestParam char[] password) {
        userService.updatePassword(userId, password);
        log.debug("Updated User's password, userId = {}, new password = {}", userId, password);
    }

    @PutMapping(value = "/user/update-email")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateEmail(@RequestBody EmailInDto email) {
        userService.updateEmail(email);
        log.debug("Updated User's email, new email = {}", email);
    }

    @PutMapping(value = "/dep-admin/users/{userId}/update-email")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateEmail(@PathVariable Long userId, @Valid @RequestBody EmailInDto email) {
        userService.updateEmail(userId, email);
        log.debug("Updated User's email, userId = {}, new email = {}", userId, email);
    }

    @PutMapping("/dep-admin/users/{userId}/disable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void disable(@PathVariable Long userId) {
        userService.deactivate(userId);
        log.debug("Disabled User, userId = {}", userId);
    }

    @PutMapping("/dep-admin/users/{userId}/enable")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable Long userId) {
        userService.activate(userId);
        log.debug("Enabled User, userId = {}", userId);
    }

}
