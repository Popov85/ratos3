package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.PasswordReset;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.PasswordResetRepository;
import ua.edu.ratos.dao.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class PasswordResetService {

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final UserSelfService userSelfService;

    private final PasswordResetRepository passwordResetRepository;

    @Autowired
    public PasswordResetService(EmailService emailService, UserRepository userRepository,
                                UserSelfService userSelfService, PasswordResetRepository passwordResetRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.userSelfService=userSelfService;
        this.passwordResetRepository = passwordResetRepository;
    }

    @Value("${sendgrid.template.resetPassword}")
    private String resetPasswordTemplate;

    @Value("${sendgrid.template.temporaryPassword}")
    private String temporaryPasswordTemplate;

    @Transactional
    public void generateSecretAndSend(@NonNull String toEmail, @NonNull String baseUrl) {
        // Find out a name of user by email, generate secret
        User user = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with such email is not found, email = " + toEmail));
        String fullName = user.getName() + " " + user.getSurname();
        // Generate a random secret
        String generatedSecret = UUID.randomUUID().toString();
        // Add an entry to a dedicated table
        PasswordReset passwordReset = new PasswordReset(toEmail, generatedSecret, "PENDING");
        passwordResetRepository.save(passwordReset);
        // Send email to a user with a link
        String passwordResetUrl = baseUrl + "/self-registration/"
                + toEmail + "/" + generatedSecret + "/do-password-reset";
        Map<String, Object> params = new HashMap<>();
        params.put("name", fullName);
        params.put("password_reset_url", passwordResetUrl);
        emailService.sendEmail(resetPasswordTemplate, toEmail, "Password reset", params);
    }

    @Transactional
    public void checkSecretAndSendTemporaryPassword(@NonNull String toEmail, @NonNull String secret) {
        // Find out a name of user by email, generate secret
        User user = userRepository.findByEmail(toEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with such email is not found, email = " + toEmail));
        String fullName = user.getName() + " " + user.getSurname();
        PasswordReset passwordReset = passwordResetRepository.findById(toEmail)
                .orElseThrow(() -> new EntityNotFoundException("Requested email is not found in password change table, email =" + toEmail));
        // Check if secret provided matches that in the database
        if (!passwordReset.getSecret().equals(secret))
            throw new RuntimeException("Secret mismatched the one that is found in the database!");
        // Generate new 8-characters password, replace and send it to the user
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        // Delete the entry from a dedicated table
        passwordResetRepository.deleteById(toEmail);
        // Update a password in user account
        userSelfService.updatePassword(user, newPassword);
        Map<String, Object> params = new HashMap<>();
        params.put("name", fullName);
        params.put("new_password", newPassword);
        emailService.sendEmail(temporaryPasswordTemplate, toEmail, "Temporary password", params);
    }
}
