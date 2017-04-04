package ua.zp.zsmu.ratos.learning_session.dao.impl;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 04.04.2017.
 */
@Repository
public class ThemeDAOJDBC {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThemeDAOJDBC.class);

        @Autowired
        private DataSource dataSource;

        private static final String SELECT = "SELECT * FROM quest WHERE quest.theme = ? ";

        public List<Question> getAllQuestions(Long themeId) {
                List<Question> questions = new ArrayList<>();
                ResultSet resultSet = null;
                Question question;
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement(SELECT)) {
                        statement.setLong(1, themeId);
                        resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                question = new Question();
                                question.setId(resultSet.getLong("id"));
                                question.setTitle(resultSet.getString("title"));
                                question.setConcept(resultSet.getString("concept"));
                                question.setHelpString(resultSet.getString("helpstr"));
                                question.setHelpSource(resultSet.getString("helpsrc"));
                                question.setLevel(resultSet.getShort("level"));
                                question.setHelpTitle(resultSet.getString("help_title"));
                                question.setHelp(resultSet.getString("help"));
                                questions.add(question);
                                LOGGER.info("GET next question: " + question);
                        }
                } catch (SQLException ex) {
                        LOGGER.error(ex.getMessage());
                        throw new RuntimeException(ex);
                } finally {
                        if (resultSet != null) {
                                //Utils.closeQuietly(resultSet);
                        }
                }
                return questions;
        }
}
