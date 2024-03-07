package ua.edu.ratos.service.dto.out.report;

import lombok.Data;

@Data
public class ReportOnContentPiece {

    private String org;
    private String fac;
    private String dep;

    // All are basically optional
    // If not requested keep it null
    private long quantityOfCourses;
    private long quantityOfLMSCourses;
    private long quantityOfSchemes;
    private long quantityOfThemes;
    private long quantityOfQuestions;
}
