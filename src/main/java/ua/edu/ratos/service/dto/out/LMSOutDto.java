package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class LMSOutDto {

    private Long lmsId;

    private String name;

    private LTICredentialsOutDto credentials;

    private LTIVersionOutDto ltiVersion;

    private Set<LMSOriginOutDto> origins;
}
