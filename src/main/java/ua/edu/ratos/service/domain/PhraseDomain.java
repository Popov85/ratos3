package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhraseDomain {

    private Long phraseId;

    private String phrase;

    // Nullable
    private ResourceDomain resourceDomain;
}
