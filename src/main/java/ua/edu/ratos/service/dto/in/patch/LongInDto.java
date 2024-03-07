package ua.edu.ratos.service.dto.in.patch;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class LongInDto {

    @Positive(message = "{dto.fk.required}")
    private Long value;
}
