package ua.edu.ratos.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ResponseMatcher implements Response {

    private List<Triple> responses;

    @Getter
    public static class Triple {
        private long answerId;
        private long leftPhraseId;
        private long rightPhraseId;
    }
}
