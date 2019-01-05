package ua.edu.ratos.service.session.decorator;

import ua.edu.ratos.service.domain.SessionData;
import ua.edu.ratos.service.dto.session.batch.BatchInDto;

/**
 * This class demonstrates how to use next processing decorator
 */
public class Usage {

    public static void main(String[] args) {
        SessionData sessionData = null;
        BatchInDto batchInDto = null;

        NextProcessorTemplate processor = new NextProcessorTemplate();
        processor.process(batchInDto, sessionData);

    }
}
