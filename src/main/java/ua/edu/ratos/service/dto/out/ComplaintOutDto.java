package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ua.edu.ratos.dao.entity.ComplaintId;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ComplaintOutDto {

    private ComplaintId complaintId;

    private String question;

    // Question questionType
    private long qtype;

    // Short name of complaint questionType
    private String complaint;

    private LocalDateTime lastComplained;

    // How many times students have complained
    // about this question+complaint questionType pair
    private int timesComplained;
}
