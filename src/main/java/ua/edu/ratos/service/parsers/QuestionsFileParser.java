package ua.edu.ratos.service.parsers;

import java.io.File;
import java.io.InputStream;

public interface QuestionsFileParser {
    QuestionsParsingResult parseFile(File file, String charset);
    QuestionsParsingResult parseStream(InputStream stream, String charset);
}
