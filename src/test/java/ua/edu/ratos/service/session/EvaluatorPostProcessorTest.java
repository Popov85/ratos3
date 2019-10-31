package ua.edu.ratos.service.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.edu.ratos.config.properties.AppProperties;
import ua.edu.ratos.service.domain.SettingsDomain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EvaluatorPostProcessorTest {

    @Mock
    private SettingsDomain settingsDomain;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AppProperties appProperties;

    @InjectMocks
    private EvaluatorPostProcessor evaluatorPostProcessor;

    @Test(timeout = 1000)
    public void applyBountyTo2dLevelQuestionTest() {
        double actual = evaluatorPostProcessor.applyBounty(70.0, (byte) 2, 1.1f, 1.2f);
        assertEquals("Wrong bounty score", 77, actual, 0.01);
    }


    @Test(timeout = 1000)
    public void applyPenaltyOf50PercentToFullyCorrectScoreTest() {
        when(appProperties.getSession().getTimeoutPenalty()).thenReturn(50d);
        double actual = evaluatorPostProcessor.applyPenalty(100d);
        assertEquals("Wrong penalised score", 50, actual, 0.01);
    }

    @Test(timeout = 1000)
    public void getBountyFor3dLevelQuestionTest() {
        when(settingsDomain.getLevel3Coefficient()).thenReturn(1.2f);
        Double actual = evaluatorPostProcessor.getBounty((byte) 3, settingsDomain);
        assertEquals("Wrong bounty value, %", 20, actual, 0.01);

    }

    @Test(timeout = 1000)
    public void getPenalty() {
        when(appProperties.getSession().getTimeoutPenalty()).thenReturn(50d);
        Double actual = evaluatorPostProcessor.getPenalty();
        assertEquals("Wrong penalty value, %", 50, actual, 0.01);
    }
}