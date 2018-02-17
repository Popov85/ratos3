package ua.zp.zsmu.ratos.learning_session.service.parser;

import java.util.List;

/**
 * Created by Andrey on 1/31/2018.
 */
public class ParsingResult<Question, Issue> {
    private List<Question> questions;
    private List<Issue> issues;

    public ParsingResult(List<Question> questions, List<Issue> issues) {
        this.questions = questions;
        this.issues = issues;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
