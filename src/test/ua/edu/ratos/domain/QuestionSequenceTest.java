package ua.edu.ratos.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class QuestionSequenceTest {

    @Test
    public void test() {
        List<Long> listResponse = Arrays.asList(123L,522L,541L,178L,963L);
        List<Long> listCorrect = Arrays.asList(123L,522L,541L,178L,963L);
        Assert.assertEquals(listCorrect, listResponse);
        if (listResponse.equals(listCorrect)) System.out.println("Equals");
    }

}
