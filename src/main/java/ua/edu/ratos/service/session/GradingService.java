package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.GradingRepository;
import ua.edu.ratos.service.domain.GradingDomain;
import ua.edu.ratos.service.dto.out.AccessOutDto;
import ua.edu.ratos.service.dto.out.GradingOutDto;
import ua.edu.ratos.service.session.grade.GradedResult;
import ua.edu.ratos.service.session.grade.Grader;
import ua.edu.ratos.service.session.grade.GradingFactory;
import ua.edu.ratos.service.transformer.entity_to_dto.GradingDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GradingService {

    private final GradingRepository gradingRepository;

    private final GradingDtoTransformer gradingDtoTransformer;

    private final GradingFactory gradingFactory;

    /**
     * Grader the overall result based on grading system specified by settings
     * @param percent
     * @param schemeId
     * @param gradingDomain
     * @return overall outcome in the given scale
     */
    public GradedResult grade(@NonNull final Long schemeId, @NonNull final GradingDomain gradingDomain, final double percent) {
        final Grader grader = gradingFactory.getInstance(gradingDomain.getName());
        GradedResult grade = grader.grade(percent, schemeId);
        return grade;
    }

    @Transactional(readOnly = true)
    public Set<GradingOutDto> findAllGradingsForDropDown() {
        return gradingRepository.findAll()
                .stream()
                .map(gradingDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

}
