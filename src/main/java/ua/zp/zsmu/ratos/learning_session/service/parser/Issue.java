package ua.zp.zsmu.ratos.learning_session.service.parser;

/**
 * Created by Andrey on 1/31/2018.
 */
public class Issue {
    // The severity of this parsing issue
    public enum Severity {
        LOW, MEDIUM, HIGH
    }

    // The part of the file where faced with this issue
    public enum Part {
        QUESTION, HEADER, ANSWER, HINT
    }

    private String description;
    private int line;// if -1 then line is undefined
    private String title;
    private Severity severity;
    private Part part;

    public Issue(String description, Severity severity, Part part) {
        this.description = description;
        this.severity = severity;
        this.part = part;
        this.title="";
        this.line = -1;
    }

    public Issue setLine(int line) {
        this.line = line;
        return this;
    }

    public Issue setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public int getLine() {
        return line;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Part getPart() {
        return part;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "Issue{" +
                "description='" + description + '\'' +
                ", line=" + line +
                ", title='" + title + '\'' +
                ", severity=" + severity +
                ", part=" + part +
                '}';
    }
}
