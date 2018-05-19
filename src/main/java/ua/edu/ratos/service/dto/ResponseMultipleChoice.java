package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ResponseMultipleChoice implements Response {
    private List<Long> ids;
}
