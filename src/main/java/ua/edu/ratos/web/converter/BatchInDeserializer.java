package ua.edu.ratos.web.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ua.edu.ratos.service.dto.response.Response;
import ua.edu.ratos.service.dto.response.*;
import ua.edu.ratos.service.dto.session.BatchIn;
import ua.edu.ratos.service.dto.session.OptionsDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Slf4j
public class BatchInDeserializer extends JsonDeserializer<BatchIn> {

    private static final String PARSING_ERROR = "JSON parsing error";

    @Override
    public BatchIn deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {

        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        Map<Long, Response> responses = new HashMap<>();

        addResponses(root, "mcq", responses, (n, id) -> mcq(n, id));
        addResponses(root, "fbsq", responses, (n, id) -> fbsq(n, id));
        addResponses(root, "fbmq", responses, (n, id) -> fbmq(n, id));
        addResponses(root, "mq", responses, (n, id) -> mq(n, id));
        addResponses(root, "sq", responses, (n, id) -> sq(n, id));

        Map<Long, OptionsDto> options = new HashMap<>();

        addOptions(root, "options", options);

        return new BatchIn(responses, options);
    }

    private void addResponses(JsonNode root, String nodeName, Map<Long, Response> responses,
                              BiFunction<JsonNode, Long, Response> biFunction) {
        JsonNode node = root.path(nodeName);
        if (!node.isMissingNode()) {
            node.forEach(n -> {
                final long questionId = n.path("questionId").asLong();
                if (questionId <= 0) throw new RuntimeException(PARSING_ERROR);
                responses.put(questionId, biFunction.apply(node, questionId));
            });
        }
    }

    private void addOptions(JsonNode root, String nodeName, Map<Long, OptionsDto> options) {
        JsonNode node = root.path(nodeName);
        if (!node.isMissingNode()) {
            node.forEach(n -> {
                final long questionId = n.path("questionId").asLong();
                if (questionId <= 0) throw new RuntimeException(PARSING_ERROR);
                final boolean helpRequested = n.path("helpRequested").asBoolean();
                final boolean skipRequested = n.path("skipRequested").asBoolean();
                final boolean starRequested = n.path("starRequested").asBoolean();
                final boolean complainRequested = n.path("complainRequested").asBoolean();
                options.put(questionId, new OptionsDto(helpRequested, skipRequested, starRequested, complainRequested));
            });
        }
    }

    private ResponseMultipleChoice mcq(JsonNode n, Long questionId) {
        Set<Long> answersIds = new HashSet<>();
        n.path("answersIds").forEach(id -> answersIds.add(id.asLong()));
        return new ResponseMultipleChoice(questionId, answersIds);
    }

    private ResponseFillBlankSingle fbsq(JsonNode n, Long questionId) {
        String enteredPhrase = n.path("enteredPhrase").asText();
        return new ResponseFillBlankSingle(questionId, enteredPhrase);
    }

    private ResponseFillBlankMultiple fbmq(JsonNode n, Long questionId) {
        Set<ResponseFillBlankMultiple.Pair> enteredPhrases = new HashSet<>();
        n.path("enteredPhrases").forEach(pair -> {
            final long answerId = pair.path("answerId").asLong();
            if (answerId <= 0) throw new RuntimeException(PARSING_ERROR);
            final String enteredPhrase = pair.path("enteredPhrase").asText();
            enteredPhrases.add(new ResponseFillBlankMultiple.Pair(answerId, enteredPhrase));
        });
        return new ResponseFillBlankMultiple(questionId, enteredPhrases);
    }

    private ResponseMatcher mq(JsonNode n, Long questionId) {
        Set<ResponseMatcher.Triple> matchedPhrases= new HashSet<>();
        n.path("matchedPhrases").forEach(pair -> {
            final long answerId = pair.path("answerId").asLong();
            if (answerId <= 0) throw new RuntimeException(PARSING_ERROR);
            final String leftPhrase = pair.path("leftPhrase").asText();
            final String rightPhrase = pair.path("rightPhrase").asText();
            matchedPhrases.add(new ResponseMatcher.Triple(answerId, leftPhrase, rightPhrase));
        });
        return new ResponseMatcher(questionId, matchedPhrases);
    }

    private ResponseSequence sq(JsonNode n, Long questionId) {
        Set<Long> answersIds = new HashSet<>();
        n.path("orderedPhrases").forEach(id -> answersIds.add(id.asLong()));
        return new ResponseSequence(questionId, answersIds);
    }
}
