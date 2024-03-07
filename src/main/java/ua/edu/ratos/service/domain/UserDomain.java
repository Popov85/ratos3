package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

    private Long userId;

    private String name;

    private String surname;
}
