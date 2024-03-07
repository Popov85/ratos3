package ua.edu.ratos.service.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResourceOutDto {

    private Long resourceId;

    private String link;

    private String description;

    private String type;

    private short width;

    private short height;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm (Z)")
    private OffsetDateTime lastUsed;

    private StaffMinOutDto staff;
}
