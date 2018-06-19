package ua.edu.ratos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.repository.ThemeRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;


    // TODO add not deleted
    public List<Theme> findAll() {
        return StreamSupport.stream(themeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long themeId) {
        try {
            themeRepository.deleteById(themeId);
        } catch (Exception e) {
            // Soft delete:
            // 1) findAll Questions with Answers in this Theme
            // 2) Update the flag deleted for Theme, for each Question and each Answer;
        }
    }
}
