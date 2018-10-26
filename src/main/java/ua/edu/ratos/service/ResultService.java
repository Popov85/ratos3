package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.domain.entity.Result;
import ua.edu.ratos.domain.entity.ResultTheme;
import ua.edu.ratos.domain.entity.Theme;
import ua.edu.ratos.domain.entity.User;
import ua.edu.ratos.domain.repository.ResultRepository;
import ua.edu.ratos.domain.repository.ThemeRepository;
import ua.edu.ratos.domain.repository.UserRepository;
import ua.edu.ratos.service.session.domain.ResultOut;
import ua.edu.ratos.service.session.domain.SessionData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    public Long save(@NonNull final SessionData sessionData, @NonNull final ResultOut result, boolean timeOuted) {
        Result r = new Result();
        r.setScheme(sessionData.getScheme());
        r.setUser(userRepository.getOne(sessionData.getUserId()));
        r.setPassed(result.isPassed());
        r.setGrade(result.getGrade());
        r.setPercent(result.getPercent());
        r.setSessionLasted(sessionData.getProgressData().getTimeSpent());
        r.setSessionEnded(LocalDateTime.now());
        r.setTimeOuted(timeOuted);
        List<ResultTheme> resultPerThemes = new ArrayList<>();
        result.getThemeResults().forEach(res -> {
            ResultTheme resultTheme = new ResultTheme();
            resultTheme.setTheme(themeRepository.getOne(res.getThemeId()));
            resultTheme.setResult(r);
            resultTheme.setPercent(res.getPercent());
            resultPerThemes.add(resultTheme);
        });
        r.setResultTheme(resultPerThemes);
        resultRepository.save(r);
        return r.getResId();
    }
}
