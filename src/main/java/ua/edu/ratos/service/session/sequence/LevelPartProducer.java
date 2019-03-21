package ua.edu.ratos.service.session.sequence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LevelPartProducer {

    private CollectionShuffler collectionShuffler;

    @Autowired
    public void setCollectionShuffler(CollectionShuffler collectionShuffler) {
        this.collectionShuffler = collectionShuffler;
    }

    public List<Question> getLevelPart(Set<Question> typeList, byte level, short requestedQuantity) {
        List<Question> levelList = getLevelList(typeList, level);
        // Eliminate construction phase mistake: if requested level of this type not found (mistake during test construction), return empty list (as a fallback)
        if (levelList.isEmpty()) return Collections.emptyList();
        // Eliminate construction phase mistake: if the actual list from DB is less than is requested, return a reduced number of questions (as a fallback)
        if (requestedQuantity > levelList.size()) requestedQuantity = (short) levelList.size();
        // make sure all "required" questions are included into the result list
        List<Question> requiredList = getRequiredList(levelList).get(true);
        if (requiredList.size()==requestedQuantity) return requiredList;
        // rare case, required questions are majority and the resulting list will contain only them
        if (requiredList.size()>requestedQuantity) return collectionShuffler.shuffle(requiredList, requestedQuantity);
        // normal case, required questions are just minority and we still need to randomly select non-required to satisfy request
        List<Question> resultLevel = new ArrayList<>(requiredList);
        // how many non-required questions should be shuffled
        int dif = requestedQuantity - requiredList.size();
        List<Question> nonRequiredList = getRequiredList(levelList).get(false);
        List<Question> shuffled = collectionShuffler.shuffle(nonRequiredList, dif);
        resultLevel.addAll(shuffled);
        return resultLevel;
    }

    private List<Question> getLevelList(Set<Question> questions, byte level) {
        return questions.stream()
                .filter(q -> q.getLevel()==level)
                .collect(Collectors.toList());
    }

    private Map<Boolean, List<Question>> getRequiredList(List<Question> levelList) {
        return levelList.stream().collect(Collectors.partitioningBy(Question::isRequired));
    }
}
