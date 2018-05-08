package ua.edu.ratos.service.parsers;

import java.io.File;

public interface FileParser {
    ParsingResult parseFile(File filename, String charset);
}
