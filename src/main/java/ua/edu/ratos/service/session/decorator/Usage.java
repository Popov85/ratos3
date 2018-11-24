package ua.edu.ratos.service.session.decorator;

import ua.edu.ratos.service.session.domain.SessionData;
import ua.edu.ratos.service.session.dto.batch.BatchInDto;

public class Usage {

    public static void main(String[] args) {
        SessionData sessionData = null;
        BatchInDto batchInDto = null;

        NextProcessorTemplate processor = new NextProcessorTemplate();
        processor.process(batchInDto, sessionData);

    }
}
