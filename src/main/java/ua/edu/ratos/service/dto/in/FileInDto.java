package ua.edu.ratos.service.dto.in;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.*;

@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileInDto {

    @Positive(message = "Invalid themeId, {dto.fk.required}")
    private long themeId;

    @Positive(message = "Invalid langId, {dto.fk.required}")
    private long langId;

    @Positive(message = "Invalid staffId, {dto.fk.required}")
    private long staffId;
    /**
     * Specifies if the user has accepted the parsing result info and agreed to savePoints the totalByType into DB
     */
    private boolean confirmed;
}
