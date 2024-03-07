package ua.edu.ratos.service.dto.out.report;

import lombok.Data;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ReportOnContent {
    private Set<ReportOnContentPiece> data = new HashSet<>();
    private LocalDateTime requestedAt;
    private StaffMinOutDto requestedBy;
}
