package ua.edu.ratos.service.dto;

import lombok.ToString;
import java.util.List;

@ToString
public class CorrectAnswerMultipleChoiceDto {
    public long questionId;
    public List<Triple> ids;

    public static class Triple {
        public long answerId;
        public short percent;
        public boolean isRequired;
    }
}
