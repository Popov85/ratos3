package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.StaffRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.EmailInDto;
import ua.edu.ratos.service.dto.in.UserMinInDto;

@Service
public class StaffSelfService {

    private SecurityUtils securityUtils;

    private StaffRepository staffRepository;

    private PasswordEncoder encoder;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setStaffRepository(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    //---------------------------------------------------ACCOUNT update-------------------------------------------------

    @Transactional
    public void updateNameAndSurname(@NonNull final UserMinInDto dto) {
        Staff staff = staffRepository.findById(securityUtils.getAuthStaffId()).get();
        User user = staff.getUser();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
    }

    @Transactional
    public void updateEmail(@NonNull final EmailInDto email) {
        Staff staff = staffRepository.findById(securityUtils.getAuthStaffId()).get();
        User user = staff.getUser();
        user.setEmail(email.getEmail());
    }

    @Transactional
    public void updatePassword(@NonNull final char[] oldPassword, @NonNull final char[] newPassword) {
        Staff staff = staffRepository.findById(securityUtils.getAuthStaffId()).get();
        User user = staff.getUser();
        if (!encoder.matches(new String(oldPassword), new String(user.getPassword())))
            throw new RuntimeException("Old password does not match the one provided");
        user.setPassword(encoder.encode(new String(newPassword)).toCharArray());
    }
}
