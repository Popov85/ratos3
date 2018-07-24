package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.ThemeView;
import ua.edu.ratos.domain.repository.ThemeViewRepository;
import ua.edu.ratos.service.dto.entity.ThemeViewOutDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ThemeViewService {
    @Autowired
    ThemeViewRepository themeViewRepository;

    public ThemeViewOutDto findByThemeId(Long themeId) {
        final List<ThemeView> themeViews = themeViewRepository.findAllByThemeId(themeId);
        final int totalQuantity = themeViews.
                stream().
                mapToInt(t -> t.getTotal()).
                sum();
        return new ThemeViewOutDto(themeViews, totalQuantity);
    }
}
