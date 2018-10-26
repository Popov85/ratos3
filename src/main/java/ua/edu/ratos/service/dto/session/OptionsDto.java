package ua.edu.ratos.service.dto.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class OptionsDto {
    private final boolean helpRequested;
    private final boolean skipRequested;
    private final boolean starRequested;
    private final boolean complainRequested;
}
