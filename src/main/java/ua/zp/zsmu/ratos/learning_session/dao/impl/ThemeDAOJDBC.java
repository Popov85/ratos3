package ua.zp.zsmu.ratos.learning_session.dao.impl;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 4/15/2017.
 */
@Repository
public class ThemeDAOJDBC {
        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThemeDAOJDBC.class);

        @Autowired
        private DataSource dataSource;

        private static final String SELECT_ONE = "SELECT * FROM theme WHERE id = ? ";

        private static final String SELECT_ALL = "SELECT * FROM theme";


        public List<Theme> getAllThemes(Long themeId) {
                List<Theme> themes = new ArrayList<>();
                ResultSet resultSet = null;
                Theme theme;
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
                        resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                InputStream title = resultSet.getBinaryStream("title");
                                theme = new Theme();
                                theme.setId(resultSet.getLong("id"));
                                theme.setTitle(resultSet.getString("title"));
                                themes.add(theme);
                                LOGGER.info("GOT next theme: " + theme);
                        }
                } catch (SQLException ex) {
                        LOGGER.error(ex.getMessage());
                        throw new RuntimeException(ex);
                } finally {
                        if (resultSet != null) {
                                //Utils.closeQuietly(resultSet);
                        }
                }
                return themes;
        }

        public Theme getOneTheme(Long themeId) {
                ResultSet resultSet = null;
                Theme theme = null;
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement statement = connection.prepareStatement(SELECT_ONE)) {
                        statement.setLong(1, themeId);
                        resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                                InputStream title = resultSet.getBinaryStream("title");
                                BufferedReader br = new BufferedReader(
                                        new InputStreamReader(title, "windows-1251"));
                                String decodedTitle = br.readLine();
                                br.close();
                                System.out.println("Decoded string: "+decodedTitle);

                                theme = new Theme();
                                theme.setId(resultSet.getLong("id"));
                                theme.setTitle(decodedTitle);
                                //theme.setTitle(resultSet.getString("title"));
                                LOGGER.info("GOT next theme: " + theme);
                        }
                } catch (SQLException ex) {
                        LOGGER.error(ex.getMessage());
                        throw new RuntimeException(ex);
                } catch (UnsupportedEncodingException e) {
                        LOGGER.error("ENCODING ERROR: "+e.getMessage());
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        if (resultSet != null) {
                                // Utils.closeQuietly(resultSet);
                        }
                }
                return theme;
        }

}
