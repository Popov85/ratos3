package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.EmailInDto;

import javax.persistence.EntityNotFoundException;

/**
 * Technically we should allow editing users of org, fac, dep ONLY by corresponding admins,
 * but for the sake of simplicity we omit implementing this functionality, and thus we trust front-end
 * to deal with possible contentions!
 */
@Service
@AllArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND = "The requested User is not found, userId = ";

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final SecurityUtils securityUtils;



    @Transactional
    public void updateName(@NonNull final String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Invalid name");
        User user = userRepository.findById(securityUtils.getAuthUserId()).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND));
        user.setName(name);
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateName(@NonNull final Long userId, @NonNull final String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Invalid name");
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setName(name);
    }

    @Transactional
    public void updateSurname(@NonNull final String surname) {
        if (surname.isEmpty()) throw new IllegalArgumentException("Invalid surname");
        User user = userRepository.findById(securityUtils.getAuthUserId()).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND));
        user.setSurname(surname);
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateSurname(@NonNull final Long userId, @NonNull final String surname) {
        if (surname.isEmpty()) throw new IllegalArgumentException("Invalid surname");
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setSurname(surname);
    }

    @Transactional
    public void updatePassword(@NonNull final char[] password) {
        if (password.length<8) throw new IllegalArgumentException("Invalid password");
        User user = userRepository.findById(securityUtils.getAuthUserId()).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND));
        user.setPassword(encoder.encode(new String(password)).toCharArray());
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updatePassword(@NonNull final Long userId, @NonNull final char[] password) {
        if (password.length<8) throw new IllegalArgumentException("Invalid password");
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setPassword(encoder.encode(new String(password)).toCharArray());
    }

    @Transactional
    public void updateEmail(@NonNull final EmailInDto email) {
        if (userRepository.findByEmail(email.getEmail()).isPresent())
            throw new UnsupportedOperationException("Such email is already present in e-RATOS");
        User user = userRepository.findById(securityUtils.getAuthUserId()).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND));
        user.setEmail(email.getEmail());
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void updateEmail(@NonNull final Long staffId, @NonNull final EmailInDto email) {
        if (userRepository.findByEmail(email.getEmail()).isPresent())
            throw new UnsupportedOperationException("Such email is already present in e-RATOS");
        User user = userRepository.findById(staffId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setEmail(email.getEmail());
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void activate(@NonNull final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setActive(true);
    }

    @Transactional
    @Secured({"ROLE_DEP-ADMIN", "ROLE_FAC-ADMIN", "ROLE_ORG-ADMIN", "ROLE_GLOBAL-ADMIN"})
    public void deactivate(@NonNull final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setActive(false);
    }

}
