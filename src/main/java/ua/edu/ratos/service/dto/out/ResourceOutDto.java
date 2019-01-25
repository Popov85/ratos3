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
public class ResourceOutDto {

    private Long resourceId;

    private String link;

    private String description;

    private LocalDateTime lastUsed;

    private StaffMinOutDto staff;
}
