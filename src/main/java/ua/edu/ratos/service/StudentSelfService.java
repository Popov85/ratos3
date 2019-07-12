package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.entity.Student;
import ua.edu.ratos.dao.entity.User;
import ua.edu.ratos.dao.repository.StudentRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.EmailInDto;
import ua.edu.ratos.service.dto.in.UserMinInDto;

/**
 * For account adjustment from within personal cabinet
 */
@Service
public class StudentSelfService {

    private SecurityUtils securityUtils;

    private StudentRepository studentRepository;

    private PasswordEncoder encoder;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    //---------------------------------------------------ACCOUNT update-------------------------------------------------

    @Transactional
    public void updateNameAndSurname(@NonNull final UserMinInDto dto) {
        Student stud = studentRepository.findById(securityUtils.getAuthUserId()).get();
        User user = stud.getUser();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
    }

    @Transactional
    public void updateEmail(@NonNull final EmailInDto email) {
        Student stud = studentRepository.findById(securityUtils.getAuthUserId()).get();
        User user = stud.getUser();
        user.setEmail(email.getEmail());
    }

    @Transactional
    public void updatePassword(@NonNull final char[] oldPassword, @NonNull final char[] newPassword) {
        Student stud = studentRepository.findById(securityUtils.getAuthUserId()).get();
        User user = stud.getUser();
        if (!encoder.matches(new String(oldPassword), new String(user.getPassword())))
            throw new RuntimeException("Old password does not match the one provided");
        user.setPassword(encoder.encode(new String(newPassword)).toCharArray());
    }
}
