package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PhraseOutDto {

    private Long phraseId;

    private String phrase;

    private ResourceOutDto resource;
}
