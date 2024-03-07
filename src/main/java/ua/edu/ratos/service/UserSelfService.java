package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.UserRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.UserMinInDto;

@Service
@AllArgsConstructor
public class UserSelfService {

    private final UserRepository staffRepository;

    private final PasswordEncoder encoder;

    private final SecurityUtils securityUtils;

    //---------------------------------------------------ACCOUNT update-------------------------------------------------
    @Transactional
    public void updateProfile(@NonNull final UserMinInDto dto) {
        User user = staffRepository.findById(securityUtils.getAuthUserId()).get();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
    }

    //--------------------------------------------------PASSWORD update-------------------------------------------------
    @Transactional
    public void updatePassword(@NonNull final char[] oldPassword, @NonNull final char[] newPassword) {
        User user = staffRepository.findById(securityUtils.getAuthUserId()).get();
        if (!encoder.matches(new String(oldPassword), new String(user.getPassword())))
            throw new RuntimeException("Old password does not match the one provided");
        user.setPassword(encoder.encode(new String(newPassword)).toCharArray());
    }
}
