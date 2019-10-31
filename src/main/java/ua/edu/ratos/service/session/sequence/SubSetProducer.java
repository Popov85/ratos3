package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class SubSetProducer {

    private final RequiredAwareSubSetProducer requiredAwareSubSetProducer;

    /**
     * Randomly reduces the amount of questions of given theme, type and level to the requested quantity
     * @param map map of all existing in repository questions of given theme, type and level
     * @return the requested quantity of questions of given theme, type and level
     */
    public List<Question> getSubSet(@NonNull final Map<Affiliation, Set<Question>> map) {
        List<Question> themeResult = new ArrayList<>();
        map.entrySet().forEach((e) -> {
            Affiliation key = e.getKey();
            Set<Question> questions = e.getValue();
            if (key.getQuantity()!=0) {
                short requestedQuantity = key.getQuantity();
                List<Question> levelPart = requiredAwareSubSetProducer.getSubSetWithRequired(requestedQuantity, questions);
                themeResult.addAll(levelPart);
                log.debug("Added questions for themeId= {}, typeId = {}, level = {} of quantity = {}",
                        key.getThemeId(), key.getTypeId(), key.getLevel(), levelPart.size());
            }
        });
        return themeResult;
    }
}
