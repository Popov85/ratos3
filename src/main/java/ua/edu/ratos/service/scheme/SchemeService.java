package ua.edu.ratos.service.scheme;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Scheme;
import ua.edu.ratos.domain.entity.SchemeTheme;
import ua.edu.ratos.domain.repository.SchemeRepository;
import ua.edu.ratos.service.dto.entity.SchemeInDto;
import ua.edu.ratos.service.dto.transformer.DtoSchemeTransformer;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
public class SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private DtoSchemeTransformer transformer;

    @Autowired
    private SchemeGradingManagerService gradingManagerService;


    /**
     * By design, we cannot cascade save the needed grading details
     * We do it separately in the same transaction, depending on the grading type provided
     * and ID of selected details
     * @param dto from frontend
     * @return saved schemeId
     */
    @Transactional
    public Long save(@NonNull SchemeInDto dto) {
        Scheme scheme = transformer.fromDto(dto);
        final Long schemeId = schemeRepository.save(scheme).getSchemeId();
        // Save gradingDetails along with scheme itself
        final long gradingId = dto.getGradingId();
        final long gradingDetailsId = dto.getGradingDetailsId();
        gradingManagerService.save(schemeId, gradingId, gradingDetailsId);
        return schemeId;
    }

    /**
     * For updates we have 2 cases (about managing grading type):
     * 1) Grading type has been changed;
     * 2) Grading remained the same (but detailed settings of this type could have been changed)
     * @param schemeId
     * @param dto
     */
    @Transactional
    public void update(@NonNull Long schemeId, @NonNull SchemeInDto dto) {
        // We do not want to create a new in case of absence
        if (!schemeRepository.existsById(schemeId))
            throw new RuntimeException("Failed to update scheme: ID does not exist");
        final long newGradingId = dto.getGradingId();
        final long gradingDetailsId = dto.getGradingDetailsId();
        // load scheme with old grading for details
        // if changed - delete old
        final Scheme scheme = schemeRepository.findByIdWithGrading(schemeId);
        long oldGradingId = scheme.getGrading().getGradingId();
        if (oldGradingId != newGradingId) {
            gradingManagerService.save(schemeId, newGradingId, gradingDetailsId);
            gradingManagerService.remove(schemeId, oldGradingId);
        } else {// grading type didn't change
            // but specific settings could have been changed, {ID=4 -> ID=5}, save plays like UPDATE
            gradingManagerService.save(schemeId, oldGradingId, gradingDetailsId);
        }
        schemeRepository.save(transformer.fromDto(schemeId, dto));
    }

    @Transactional
    public void deleteById(@NonNull Long schemeId) {
        schemeRepository.findById(schemeId).get().setDeleted(true);
    }

    /**
     * Reorder themes within a scheme by sending List<Long> and by updating each SchemeTheme with new order parameter
     * @param schemeId
     * @param schemeThemeIds
     */
    @Transactional
    public void reOrder(@NonNull Long schemeId, @NonNull List<Long> schemeThemeIds) {
        final Scheme scheme = schemeRepository.findByIdWithThemes(schemeId);
        if (scheme.getSchemeThemes().size()!=schemeThemeIds.size())
            throw new RuntimeException("Unequal list size: cannot reorder");
        scheme.clearSchemeTheme();
        schemeThemeIds.forEach(id -> {
            scheme.addSchemeTheme(em.find(SchemeTheme.class, id));
        });
    }

    /**
     * Remove an existing theme by its currentIndex from the scheme: load the List<SchemeTheme> of the given Scheme by SchemeId, and remove the currentIndex from it,
     * commit the transaction;
     * @param schemeId
     * @param index
     */
    @Transactional
    public void deleteByIndex(@NonNull Long schemeId, @NonNull Integer index) {
        if (index<0 || index>100)
            throw new RuntimeException("Wrong currentIndex to delete");
        final Scheme scheme = schemeRepository.findByIdWithThemes(schemeId);
        final SchemeTheme schemeTheme = scheme.getSchemeThemes().get(index);
        scheme.removeSchemeTheme(schemeTheme);
        if (scheme.getSchemeThemes().isEmpty()) scheme.setCompleted(false);
    }


    /*---------------------SELECT-------------------*/

    /**
     * Gets Scheme instance for a student session request
     * @param schemeId
     * @return Scheme object preferably from L2C
     */
    @Transactional(readOnly = true)
    public Scheme findByIdForSession(@NonNull Long schemeId) {
        return schemeRepository.findByIdForSession(schemeId);
    }

}
