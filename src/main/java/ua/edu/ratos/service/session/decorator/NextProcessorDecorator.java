package ua.edu.ratos.service.session.decorator;

public abstract class NextProcessorDecorator implements NextProcessor {

    protected NextProcessor nextProcessor;

    public NextProcessorDecorator(NextProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }
}
