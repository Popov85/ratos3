package ua.edu.ratos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.edu.ratos.domain.repository.ThemeRepository;
import ua.edu.ratos.domain.entity.Theme;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ThemeController {

    @Autowired
    private ThemeRepository themeRepository;

    @ResponseBody
    @RequestMapping("/theme/findAll")
    public List<Theme> findAll () {
        return StreamSupport
                .stream(themeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
