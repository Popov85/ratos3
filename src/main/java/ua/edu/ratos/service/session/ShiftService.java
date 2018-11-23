package ua.edu.ratos.service.session;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.edu.ratos.service.session.domain.question.Question;
import ua.edu.ratos.service.session.domain.SessionData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SessionData mutator!
 * Changes the state of the questions list
 */
@Slf4j
@Service
public class ShiftService {

    /**
     * Mutates the state of SessionData
     * Implements the possibility to skip a given question by moving it
     * to the end of the session list
     * @param idToShift
     * @param sessionData
     */
    public void doShift(@NonNull final Long idToShift, @NonNull final SessionData sessionData) {
        final List<Question> all = sessionData.getQuestions();
        final Map<Long, Question> questionsMap = sessionData.getQuestionsMap();
        final Question toShift = questionsMap.get(idToShift);
        // 1. Remove the skipped question from the list
        all.remove(toShift);
        // & addAnswer it to the end
        all.add(toShift);
        // 2. Update index
        final int currentIndex = sessionData.getCurrentIndex();
        sessionData.setCurrentIndex(currentIndex-1);
        log.debug("Skipped question with ID :: {}", idToShift);
    }

    /**
     * Mutates the state of SessionData
     * Implements Pyramid algorithm: moves the incorrectly answered questions to the end of the questions list
     * And
     * Updates the current index
     * @param idsToShift
     * @param sessionData
     */
    public void doShift(@NonNull final List<Long> idsToShift, @NonNull final SessionData sessionData) {
        final List<Question> all = sessionData.getQuestions();
        final Map<Long, Question> questionsMap = sessionData.getQuestionsMap();
        final List<Question> toShift = idsToShift
                .stream()
                .map(questionsId -> questionsMap.get(questionsId))
                .collect(Collectors.toList());
        // 1. Remove all the shifted questions from the list
        all.removeAll(toShift);
        // & addAnswer them to the end
        all.addAll(toShift);
        // 2. Update index
        final int currentIndex = sessionData.getCurrentIndex();
        final int quantity = toShift.size();
        sessionData.setCurrentIndex(currentIndex-quantity);
        log.debug("Shifted :: {} questions", toShift.size());
    }
}
