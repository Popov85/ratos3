package ua.edu.ratos.service.dto.out.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.ratos.service.dto.out.StaffMinOutDto;
import ua.edu.ratos.service.dto.out.criteria.ResultOfStudentForReportOutDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportOnResults {
    private List<ResultOfStudentForReportOutDto> data;
    private LocalDateTime requestedAt;
    private StaffMinOutDto requestedBy;
}
