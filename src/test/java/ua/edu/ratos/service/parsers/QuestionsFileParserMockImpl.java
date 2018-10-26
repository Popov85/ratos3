package ua.edu.ratos.service.parsers;

public class QuestionsFileParserMockImpl extends AbstractQuestionsFileParser {
    @Override
    protected void parseLine(String line) {
        if (startStatus ==false) throw new IllegalStateException("Parsing control yet not started!");
    }
}
