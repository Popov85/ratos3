package ua.edu.ratos.service.transformer.mapper;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordMapper {

    private final PasswordEncoder encoder;

    @EncodedMapping
    public char[] map(@NonNull final char[] password) {
        return encoder.encode(new String(password)).toCharArray();
    }
}
