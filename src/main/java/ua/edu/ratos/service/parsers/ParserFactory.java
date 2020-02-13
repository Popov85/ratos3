package ua.edu.ratos.service.parsers;

import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ParserFactory {
    /**
     *
     * @param extension an extension of a file with a bunch of question MCQ
     * @return the proper parser
     */
    public QuestionsFileParser getParser(@NonNull final String extension) {
        if ("txt".equals(extension)) return new QuestionsFileParserTXT();
        if ("rtp".equals(extension) || "xtt".equals(extension)) return new QuestionsFileParserRTP();
        throw new UnsupportedOperationException("Unsupported file extension: only .txt/.rtp/.xtt files are supported");
    }
}
