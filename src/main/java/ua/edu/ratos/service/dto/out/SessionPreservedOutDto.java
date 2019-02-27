package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SessionPreservedOutDto {

    private String uuid;

    private String scheme;

    private LocalDateTime whenPreserved;

    // How much job is already done (%)
    private double progress;

}
