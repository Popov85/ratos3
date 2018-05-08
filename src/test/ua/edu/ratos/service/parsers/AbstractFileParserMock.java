package ua.edu.ratos.service.parsers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractFileParserMock extends AbstractFileParser {
    @Override
    protected void parseLine(String line) {
        if (startStatus ==false) throw new IllegalStateException("Parsing process yet not started!");
        log.debug("line : {} ", line);
    }
}
