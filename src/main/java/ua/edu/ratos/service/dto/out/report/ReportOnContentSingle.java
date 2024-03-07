package ua.edu.ratos.service.dto.out.report;

import lombok.Data;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReportOnContentSingle {
    private ReportOnContentPiece data;
    private LocalDateTime requestedAt;
    private StaffMinOutDto requestedBy;
}
