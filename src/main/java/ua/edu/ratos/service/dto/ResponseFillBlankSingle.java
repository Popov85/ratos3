package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseFillBlankSingle implements Response {
    private String enteredPhrase;
}
