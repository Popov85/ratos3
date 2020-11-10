package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.edu.ratos.service.PasswordResetService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@AllArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value="/self-registration/{email}/password-reset")
    public void passwordReset(@PathVariable String email, HttpServletRequest request) {
        log.debug("Requested password reset for email = {}", email);
        String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length())
                + request.getContextPath();
        passwordResetService.generateSecretAndSend(email, baseUrl);
    }

    @GetMapping(value="/self-registration/{email}/{secret}/do-password-reset")
    public String doPasswordReset(@PathVariable String email, @PathVariable String secret, ModelMap model) {
        log.debug("Requested password reset for email = {}, secret = {}", email, secret);
        try {
            passwordResetService.checkSecretAndSendTemporaryPassword(email, secret);
            model.addAttribute("error", false);
            model.addAttribute("message", "Successfully sent an email with a temp password!");
            return "password-reset";
        } catch (Exception e) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Failure: "+e.getMessage());
            return "password-reset";
        }
    }
}
