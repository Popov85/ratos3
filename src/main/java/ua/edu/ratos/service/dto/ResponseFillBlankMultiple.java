package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Setter
@Getter
@ToString
public class ResponseFillBlankMultiple implements Response {
    private List<Pair> enteredPhrases;

    @Getter
    public static class Pair {
        private Long phraseId;
        private String enteredPhrase;
    }
}
