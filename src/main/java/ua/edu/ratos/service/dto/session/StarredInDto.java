package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class StarredInDto {

    @Positive(message = "{dto.fk.required}")
    private Long questionId;

    @Min(value = 1)
    @Max(value = 5)
    private byte stars;
}
