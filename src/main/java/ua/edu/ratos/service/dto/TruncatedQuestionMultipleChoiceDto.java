package ua.edu.ratos.service.dto;

import lombok.ToString;
import ua.edu.ratos.domain.entity.Resource;

import java.util.List;
import java.util.Optional;

@ToString
public class TruncatedQuestionMultipleChoiceDto {
    public long questionId;
    public String question;
    public Optional<Resource> resource;
    public List<TruncatedAnswerMultipleChoiceDto> answers;
}
