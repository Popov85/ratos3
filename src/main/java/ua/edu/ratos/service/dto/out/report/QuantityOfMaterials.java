package ua.edu.ratos.service.dto.out.report;

import lombok.Data;

@Data
public class QuantityOfMaterials {
    private long quantityOfCourses;
    private long quantityOfLMSCourses;
    private long quantityOfSchemes;
    private long quantityOfThemes;
    private long quantityOfQuestions;
}
