package ua.edu.ratos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InitController {

    private static final String SQL_INIT_PATH = "/init_data.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/init")
    public void init() throws Exception {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new ClassPathResource(SQL_INIT_PATH));
        log.debug("DB initialized!");
    }
}
