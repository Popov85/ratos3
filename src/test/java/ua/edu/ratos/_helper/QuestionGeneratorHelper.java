package ua.edu.ratos._helper;

import ua.edu.ratos.service.domain.*;
import ua.edu.ratos.service.domain.answer.*;
import ua.edu.ratos.service.domain.question.*;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Provides methods for generating questions of given type
 */
public class QuestionGeneratorHelper {

    public QuestionMCQDomain createMCQ(Long questionId, String question) {
        QuestionMCQDomain questionMCQ = new QuestionMCQDomain();
        questionMCQ.setQuestionId(questionId);
        questionMCQ.setQuestion(question);
        questionMCQ.setType(1L);
        questionMCQ.setLang("EN");
        questionMCQ.setLevel((byte) 1);
        questionMCQ.setSingle(true);
        questionMCQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionMCQ.setHelpDomain(new HelpDomain().setHelpId(1L).setName("HelpDomain MCQ#1").setHelp("See HelpDomain MCQ#1")
                .setResourceDomain(new ResourceDomain().setResourceId(1L).setDescription("ResourceDomain helpDomain MCQ").setLink("https://resourceDomains.com/#1")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(2L).setDescription("ResourceDomain question #2").setLink("https://resourceDomains.com/#2");
        questionMCQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));
        // ---answers---
        AnswerMCQDomain answer0 = new AnswerMCQDomain().setAnswerId(1L).setAnswer("Answer#1").setPercent((short)100).setRequired(true)
                .setResourceDomain(new ResourceDomain().setResourceId(3L).setDescription("ResourceDomain answer #1").setLink("https://resourceDomains.com/#3"));
        AnswerMCQDomain answer1 = new AnswerMCQDomain().setAnswerId(2L).setAnswer("Answer#2").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(4L).setDescription("ResourceDomain answer #2").setLink("https://resourceDomains.com/#4"));
        AnswerMCQDomain answer2 = new AnswerMCQDomain().setAnswerId(3L).setAnswer("Answer#3").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(5L).setDescription("ResourceDomain answer #3").setLink("https://resourceDomains.com/#5"));
        AnswerMCQDomain answer3 = new AnswerMCQDomain().setAnswerId(4L).setAnswer("Answer#4").setPercent((short)0).setRequired(false)
                .setResourceDomain(new ResourceDomain().setResourceId(6L).setDescription("ResourceDomain answer #4").setLink("https://resourceDomains.com/#6"));

        questionMCQ.setAnswers(new HashSet<>(Arrays.asList(answer0, answer1, answer2, answer3)));
        return questionMCQ;
    }

    public QuestionFBSQDomain createFBSQ(Long questionId, String question) {
        QuestionFBSQDomain questionFBSQ = new QuestionFBSQDomain();
        questionFBSQ.setQuestionId(questionId);
        questionFBSQ.setQuestion(question);
        questionFBSQ.setType(2L);
        questionFBSQ.setLang("EN");
        questionFBSQ.setLevel((byte) 1);
        questionFBSQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionFBSQ.setHelpDomain(new HelpDomain().setHelpId(2L).setName("HelpDomain FBSQ #1").setHelp("See HelpDomain FBSQ #1")
                .setResourceDomain(new ResourceDomain().setResourceId(7L).setDescription("ResourceDomain helpDomain FBSQ").setLink("https://resourceDomains.com/#7")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(8L).setDescription("ResourceDomain question #2").setLink("https://resourceDomains.com/#8");
        questionFBSQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // -----answer-----// phrases with no resourceDomains here
        PhraseDomain p0 = new PhraseDomain().setPhraseId(1L).setPhrase("PhraseDomain #1");
        PhraseDomain p1 = new PhraseDomain().setPhraseId(2L).setPhrase("PhraseDomain #2");
        PhraseDomain p2 = new PhraseDomain().setPhraseId(3L).setPhrase("PhraseDomain #3");
        PhraseDomain p3 = new PhraseDomain().setPhraseId(4L).setPhrase("PhraseDomain #4");

        questionFBSQ.setAnswer(new AnswerFBSQDomain().setAnswerId(1L).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p0, p1, p2, p3))));

        return questionFBSQ;
    }

    public SettingsFBDomain createSettingsFB() {
        return new SettingsFBDomain()
                .setSettingsId(1L)
                .setName("SettingsDomain #1")
                .setTypoAllowed(false)
                .setCaseSensitive(true)
                .setLang("EN")
                .setNumeric(false)
                .setSymbolsLimit((short)10)
                .setWordsLimit((short)1);
    }

    public QuestionFBMQDomain createFBMQ(Long questionId, String question, boolean partialResponse) {
        QuestionFBMQDomain questionFBMQ = new QuestionFBMQDomain();
        questionFBMQ.setQuestionId(questionId);
        questionFBMQ.setQuestion(question);
        questionFBMQ.setType(3L);
        questionFBMQ.setLang("EN");
        questionFBMQ.setLevel((byte) 1);
        questionFBMQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionFBMQ.setHelpDomain(new HelpDomain().setHelpId(3L).setName("HelpDomain FBMQ #3").setHelp("See HelpDomain FBMQ #3")
                .setResourceDomain(new ResourceDomain().setResourceId(9L).setDescription("ResourceDomain helpDomain FBMQ").setLink("https://resourceDomains.com/#9")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(10L).setDescription("ResourceDomain question #3").setLink("https://resourceDomains.com/#10");
        questionFBMQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // -----answers----- //phrases with no resourceDomains here too

        PhraseDomain p10 = new PhraseDomain().setPhraseId(5L).setPhrase("PhraseDomain #1 for FBMQ answer #1");
        PhraseDomain p11 = new PhraseDomain().setPhraseId(6L).setPhrase("PhraseDomain #2 for FBMQ answer #1");
        PhraseDomain p12 = new PhraseDomain().setPhraseId(7L).setPhrase("PhraseDomain #3 for FBMQ answer #1");
        PhraseDomain p13 = new PhraseDomain().setPhraseId(8L).setPhrase("PhraseDomain #4 for FBMQ answer #1");

        AnswerFBMQDomain a0 = new AnswerFBMQDomain().setAnswerId(1L).setPhrase("Target phraseDomain for FBMQ answer #1").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p10, p11, p12, p13)));

        PhraseDomain p20 = new PhraseDomain().setPhraseId(9L).setPhrase("PhraseDomain #1 for FBMQ answer #2");
        PhraseDomain p21 = new PhraseDomain().setPhraseId(10L).setPhrase("PhraseDomain #2 for FBMQ answer #2");
        PhraseDomain p22 = new PhraseDomain().setPhraseId(11L).setPhrase("PhraseDomain #3 for FBMQ answer #2");
        PhraseDomain p23 = new PhraseDomain().setPhraseId(12L).setPhrase("PhraseDomain #4 for FBMQ answer #2");

        AnswerFBMQDomain a1 = new AnswerFBMQDomain().setAnswerId(2L).setPhrase("Target phraseDomain for FBMQ answer #2").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p20, p21, p22, p23)));

        PhraseDomain p30 = new PhraseDomain().setPhraseId(13L).setPhrase("PhraseDomain #1 for FBMQ answer #3");
        PhraseDomain p31 = new PhraseDomain().setPhraseId(14L).setPhrase("PhraseDomain #2 for FBMQ answer #3");
        PhraseDomain p32 = new PhraseDomain().setPhraseId(15L).setPhrase("PhraseDomain #3 for FBMQ answer #3");
        PhraseDomain p33 = new PhraseDomain().setPhraseId(16L).setPhrase("PhraseDomain #4 for FBMQ answer #3");

        AnswerFBMQDomain a2 = new AnswerFBMQDomain().setAnswerId(3L).setPhrase("Target phraseDomain for FBMQ answer #3").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p30, p31, p32, p33)));

        PhraseDomain p40 = new PhraseDomain().setPhraseId(17L).setPhrase("PhraseDomain #1 for FBMQ answer #4");
        PhraseDomain p41 = new PhraseDomain().setPhraseId(18L).setPhrase("PhraseDomain #2 for FBMQ answer #4");
        PhraseDomain p42 = new PhraseDomain().setPhraseId(19L).setPhrase("PhraseDomain #3 for FBMQ answer #4");
        PhraseDomain p43 = new PhraseDomain().setPhraseId(20L).setPhrase("PhraseDomain #4 for FBMQ answer #4");

        AnswerFBMQDomain a3 = new AnswerFBMQDomain().setAnswerId(4L).setPhrase("Target phraseDomain for FBMQ answer #4").setOccurrence((byte)1).setSettings(createSettingsFB())
                .setAcceptedPhraseDomains(new HashSet<>(Arrays.asList(p40, p41, p42, p43)));

        questionFBMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));
        questionFBMQ.setPartialResponseAllowed(partialResponse);

        return questionFBMQ;
    }


    public QuestionMQDomain createMQ(Long questionId, String question, boolean partialResponse) {
        QuestionMQDomain questionMQ = new QuestionMQDomain();
        questionMQ.setQuestionId(questionId);
        questionMQ.setQuestion(question);
        questionMQ.setType(4L);
        questionMQ.setLang("EN");
        questionMQ.setLevel((byte) 1);
        questionMQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionMQ.setHelpDomain(new HelpDomain().setHelpId(4L).setName("HelpDomain MQ#4").setHelp("See HelpDomain MQ#4")
                .setResourceDomain(new ResourceDomain().setResourceId(11L).setDescription("ResourceDomain helpDomain MQ").setLink("https://resourceDomains.com/#11")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(12L).setDescription("ResourceDomain question #4").setLink("https://resourceDomains.com/#12");
        questionMQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // ---answers---

        PhraseDomain p10 = new PhraseDomain().setPhraseId(21L).setPhrase("Left PhraseDomain for MQ answer #1")
                .setResourceDomain(new ResourceDomain().setResourceId(13L).setDescription("Left phraseDomain resourceDomain for answer #1").setLink("https://resourceDomains.com/#13"));
        PhraseDomain p11 = new PhraseDomain().setPhraseId(22L).setPhrase("Right PhraseDomain for MQ answer #1");
        AnswerMQDomain a0 = new AnswerMQDomain().setAnswerId(1L).setLeftPhraseDomain(p10).setRightPhraseDomain(p11);

        PhraseDomain p20 = new PhraseDomain().setPhraseId(23L).setPhrase("Left PhraseDomain for MQ answer #2")
                .setResourceDomain(new ResourceDomain().setResourceId(14L).setDescription("Left phraseDomain resourceDomain for answer #2").setLink("https://resourceDomains.com/#14"));
        PhraseDomain p21 = new PhraseDomain().setPhraseId(24L).setPhrase("Right PhraseDomain for MQ answer #2");
        AnswerMQDomain a1 = new AnswerMQDomain().setAnswerId(2L).setLeftPhraseDomain(p20).setRightPhraseDomain(p21);

        PhraseDomain p30 = new PhraseDomain().setPhraseId(25L).setPhrase("Left PhraseDomain for MQ answer #3")
                .setResourceDomain(new ResourceDomain().setResourceId(15L).setDescription("Left phraseDomain resourceDomain for answer #3").setLink("https://resourceDomains.com/#15"));
        PhraseDomain p31 = new PhraseDomain().setPhraseId(26L).setPhrase("Right PhraseDomain for MQ answer #3");
        AnswerMQDomain a2 = new AnswerMQDomain().setAnswerId(3L).setLeftPhraseDomain(p30).setRightPhraseDomain(p31);

        PhraseDomain p40 = new PhraseDomain().setPhraseId(27L).setPhrase("Left PhraseDomain for MQ answer #4")
                .setResourceDomain(new ResourceDomain().setResourceId(16L).setDescription("Left phraseDomain resourceDomain for answer #4").setLink("https://resourceDomains.com/#16"));
        PhraseDomain p41 = new PhraseDomain().setPhraseId(28L).setPhrase("Right PhraseDomain for MQ answer #4");
        AnswerMQDomain a3 = new AnswerMQDomain().setAnswerId(4L).setLeftPhraseDomain(p40).setRightPhraseDomain(p41);

        questionMQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3)));
        questionMQ.setPartialResponseAllowed(partialResponse);

        return questionMQ;
    }

    public QuestionSQDomain createSQ(Long questionId, String question) {
        QuestionSQDomain questionSQ = new QuestionSQDomain();
        questionSQ.setQuestionId(questionId);
        questionSQ.setQuestion(question);
        questionSQ.setType(5L);
        questionSQ.setLang("EN");
        questionSQ.setLevel((byte) 1);
        questionSQ.setThemeDomain(new ThemeDomain().setThemeId(1L).setName("ThemeDomain#1"));
        questionSQ.setHelpDomain(new HelpDomain().setHelpId(5L).setName("HelpDomain SQ#5").setHelp("See HelpDomain SQ#5")
                .setResourceDomain(new ResourceDomain().setResourceId(17L).setDescription("ResourceDomain helpDomain SQ").setLink("https://resourceDomains.com/#17")));
        ResourceDomain resourceDomain = new ResourceDomain().setResourceId(18L).setDescription("ResourceDomain question #4").setLink("https://resourceDomains.com/#18");
        questionSQ.setResourceDomains(new HashSet<>(Arrays.asList(resourceDomain)));

        // ---answers---
        PhraseDomain p10 = new PhraseDomain().setPhraseId(29L).setPhrase("PhraseDomain #1 for SQ answer #1")
                .setResourceDomain(new ResourceDomain().setResourceId(19L).setDescription("ResourceDomain for SQ answer #1").setLink("https://resourceDomains.com/#19"));
        AnswerSQDomain a0 = new AnswerSQDomain().setAnswerId(1L).setPhraseDomain(p10).setOrder((short)0);

        PhraseDomain p20 = new PhraseDomain().setPhraseId(30L).setPhrase("PhraseDomain #2 for SQ answer #2")
                .setResourceDomain(new ResourceDomain().setResourceId(20L).setDescription("ResourceDomain for SQ answer #2").setLink("https://resourceDomains.com/#20"));
        AnswerSQDomain a1 = new AnswerSQDomain().setAnswerId(2L).setPhraseDomain(p20).setOrder((short)1);

        PhraseDomain p30 = new PhraseDomain().setPhraseId(31L).setPhrase("PhraseDomain #3 for SQ answer #3")
                .setResourceDomain(new ResourceDomain().setResourceId(21L).setDescription("ResourceDomain for SQ answer #3").setLink("https://resourceDomains.com/#21"));
        AnswerSQDomain a2 = new AnswerSQDomain().setAnswerId(3L).setPhraseDomain(p30).setOrder((short)2);

        PhraseDomain p40 = new PhraseDomain().setPhraseId(32L).setPhrase("PhraseDomain #4 for SQ answer #4")
                .setResourceDomain(new ResourceDomain().setResourceId(22L).setDescription("ResourceDomain for SQ answer #4").setLink("https://resourceDomains.com/#22"));
        AnswerSQDomain a3 = new AnswerSQDomain().setAnswerId(4L).setPhraseDomain(p40).setOrder((short)3);

        PhraseDomain p50 = new PhraseDomain().setPhraseId(33L).setPhrase("PhraseDomain #5 for SQ answer #5")
                .setResourceDomain(new ResourceDomain().setResourceId(23L).setDescription("ResourceDomain for SQ answer #5").setLink("https://resourceDomains.com/#23"));
        AnswerSQDomain a4 = new AnswerSQDomain().setAnswerId(5L).setPhraseDomain(p50).setOrder((short)4);

        questionSQ.setAnswers(new HashSet<>(Arrays.asList(a0, a1, a2, a3, a4)));

        return questionSQ;
    }
}
