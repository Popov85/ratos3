package ua.edu.ratos.service.dto.in.report;

import lombok.Data;

@Data
public class ReportOnContentInDto {
    private boolean courses;
    private boolean lmsCourses;
    private boolean schemes;
    private boolean themes;
    private boolean questions;
}
