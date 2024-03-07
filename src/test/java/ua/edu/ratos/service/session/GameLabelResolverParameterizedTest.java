package ua.edu.ratos.service.session;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ua.edu.ratos.config.properties.AppProperties;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

@RunWith(Parameterized.class)
public class GameLabelResolverParameterizedTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private GameLabelResolver gameLabelResolver;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AppProperties appProperties;

    @Parameterized.Parameter(0)
    public int totalWins;

    @Parameterized.Parameter(1)
    public String expectedTitle;

    TreeMap<Integer, String> labels;

    @Before
    public void setUp() {
        labels = new TreeMap<>();
        labels.put(0, "Novice");
        labels.put(1, "Beginner");
        labels.put(2, "Smart");
        labels.put(3, "Mature");
        labels.put(5, "Professional");
        labels.put(20, "Expert");
        labels.put(100, "Genius");
        given(appProperties.getGame().getUserLabel()).willReturn(labels);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 0, "Novice" },
                { 1, "Beginner"},
                { 2, "Smart"},
                { 3, "Mature"},
                { 4, "Mature"},
                { 5, "Professional"},
                { 6, "Professional"},
                { 10, "Professional"},
                { 19, "Professional"},
                { 20, "Expert"},
                { 21, "Expert"},
                { 99, "Expert"},
                { 100, "Genius"},
                { 500, "Genius"},
                { 1000, "Genius"},
        });
    }

    @Test(timeout = 2000)
    public void getLabelTest() {
        String actualTitle = gameLabelResolver.getLabel(totalWins);
        assertThat("Expected winner title is not as expected when totalWins="+totalWins, actualTitle, equalTo(expectedTitle));
    }

}
