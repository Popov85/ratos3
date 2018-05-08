package ua.edu.ratos.service.dto;

import lombok.Data;
import ua.edu.ratos.domain.Question;

import java.util.List;

@Data
public class Batch {
    private String key;
    private long timeLeft;
    private int index;
    private List<Question> batch;
}
