package ua.edu.ratos.service.session.sequence;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.question.Question;
import ua.edu.ratos.service.utils.CollectionShuffler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class serves the purpose to produce a sequence of questions that is a part of the existing questions bank list
 * and making sure that all required questions will be included to the resulting list.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RequiredAwareSubSetProducer {

    private final CollectionShuffler collectionShuffler;

    /**
     * Randomly reduces the amount of questions of given theme, type, level to the requested quantity
     * making sure all required questions are included.
     * @param requestedQuantity quantity of questions requested according to scheme's settings
     * @param questions all existing questions of given theme, type and level
     * @return the requested quantity of questions of given theme, type, level and required flag
     */
    public List<Question> getSubSetWithRequired(short requestedQuantity, @NonNull final Set<Question> questions) {
        List<Question> list = new ArrayList<>(questions);
        // Eliminate construction phase mistake: if requested level of this type not found (mistake during test construction), return empty list (as a fallback)
        if (requestedQuantity==0 || list.isEmpty()) return Collections.emptyList();
        // Eliminate construction phase mistake: if the actual list from DB is less than is requested, return a reduced number of questions (as a fallback)
        if (requestedQuantity > list.size()) requestedQuantity = (short) list.size();
        // make sure all "required" questions are included into the result list
        Map<Boolean, List<Question>> map = list.stream().collect(Collectors.partitioningBy(Question::isRequired));
        List<Question> requiredList = map.get(true);
        if (requiredList.size()==requestedQuantity) return requiredList;
        // rare case, required questions are majority and the resulting list will contain only them
        if (requiredList.size()>requestedQuantity) return collectionShuffler.shuffle(requiredList, requestedQuantity);
        // normal case, required questions are just minority and we still need to randomly select non-required to satisfy request
        List<Question> result = new ArrayList<>(requiredList);
        // how many non-required questions should be shuffled
        int dif = requestedQuantity - requiredList.size();
        List<Question> nonRequiredList = map.get(false);
        List<Question> shuffled = collectionShuffler.shuffle(nonRequiredList, dif);
        result.addAll(shuffled);
        return result;
    }
}
