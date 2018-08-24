package ua.edu.ratos.service.dto.session;

import lombok.Data;
import ua.edu.ratos.domain.entity.question.Question;
import java.util.List;

@Data
public class BatchOut {
    private String key;
    private long timeLeft;
    private int index;
    private List<Question> batch;
    private boolean isLastBatch;
    // Other parameters: result, quantityLeft,
}