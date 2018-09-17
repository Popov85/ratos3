package ua.edu.ratos.service;

import lombok.NonNull;
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
import java.util.Optional;


@Service
public class SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private DtoSchemeTransformer transformer;


    @Transactional
    public Long save(@NonNull SchemeInDto dto) {
        Scheme scheme = transformer.fromDto(dto);
        return schemeRepository.save(scheme).getSchemeId();
    }

    @Transactional
    public void update(@NonNull SchemeInDto dto) {
        if (dto.getSchemeId()==null || dto.getSchemeId()==0)
            throw new RuntimeException("Invalid ID");
        Scheme scheme = transformer.fromDto(dto);
        schemeRepository.save(scheme);
    }

    /**
     * Gets Scheme instance for a student session request
     * @param schemeId
     * @return Scheme object preferably from L2C
     */
    @Transactional(readOnly = true)
    public Scheme findByIdForSession(@NonNull Long schemeId) {
        return schemeRepository.findByIdForSession(schemeId);
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
     * Remove an existing theme by its index from the scheme: load the List<SchemeTheme> of the given Scheme by SchemeId, and remove the index from it,
     * commit the transaction;
     * @param schemeId
     * @param index
     */
    @Transactional
    public void deleteByIndex(@NonNull Long schemeId, @NonNull Integer index) {
        if (index<0 || index>100)
            throw new RuntimeException("Wrong index to delete");
        final Scheme scheme = schemeRepository.findByIdWithThemes(schemeId);
        final SchemeTheme schemeTheme = scheme.getSchemeThemes().get(index);
        scheme.removeSchemeTheme(schemeTheme);
        if (scheme.getSchemeThemes().isEmpty()) scheme.setCompleted(false);
    }

    @Transactional
    public void deleteById(@NonNull Long schemeId) {
        final Optional<Scheme> scheme = schemeRepository.findById(schemeId);
        scheme.get().setDeleted(true);
    }

}
