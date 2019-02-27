package ua.edu.ratos.service.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
@Setter
@ToString
public class ComplaintInDto {

    @Positive(message = "{dto.fk.required}")
    private Long questionId;

    @NotNull(message = "{dto.collection.required}")
    private Set<@Positive Long> complaintTypeIds;

}
