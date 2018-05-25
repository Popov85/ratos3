package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.edu.ratos.service.Response;
import java.util.List;

@Setter
@Getter
@ToString
public class BatchIn {
    private String key;
    private List<Response> responses;
}
