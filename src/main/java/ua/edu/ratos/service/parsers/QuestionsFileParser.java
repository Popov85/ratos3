package ua.edu.ratos.service.parsers;

import java.io.File;

public interface QuestionsFileParser {
    QuestionsParsingResult parseFile(File filename, String charset);
}
