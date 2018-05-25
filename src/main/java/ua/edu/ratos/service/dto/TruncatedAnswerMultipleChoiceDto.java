package ua.edu.ratos.service.dto;

import lombok.ToString;
import ua.edu.ratos.domain.model.Resource;

import java.util.Optional;

@ToString
public class TruncatedAnswerMultipleChoiceDto {
    public long answerId;
    public String answer;
    public Optional<Resource> resource;
}
