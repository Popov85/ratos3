package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Setter
@Getter
@ToString
public class ResponseSequence implements Response {
    private List<Long> answer;
}
