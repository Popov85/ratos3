package ua.edu.ratos.it.service.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.edu.ratos.service.session.GameLabelResolver;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameLabelResolverTestIT {

    @Autowired
    private GameLabelResolver gameLabelResolver;

    @Test
    public void getLabelFirstTest() {
        String label = gameLabelResolver.getLabel(0);
        assertEquals("Novice", label);
    }

    @Test
    public void getLabelMiddleTest() {
        String label = gameLabelResolver.getLabel(3);
        assertEquals("Mature", label);
    }

    @Test
    public void getLabelIntermediaryTest() {
        String label = gameLabelResolver.getLabel(7);
        assertEquals("Professional", label);
    }

    @Test
    public void getLabelIntermediaryAnotherTest() {
        String label = gameLabelResolver.getLabel(55);
        assertEquals("Expert", label);

    }

    @Test
    public void getLabelAfterLastTest() {
        String label = gameLabelResolver.getLabel(120);
        assertEquals("Genius", label);

    }
}
