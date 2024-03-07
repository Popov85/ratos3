package ua.edu.ratos.dao.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.edu.ratos.dao.entity.grading.FreePointGrading;
import ua.edu.ratos.service.session.grade.GradedResult;

@RunWith(JUnit4.class)
public class FreePointGradingTest {

    private FreePointGrading freePointGrading;

    @Before
    public void init() {
        freePointGrading = new FreePointGrading();
        freePointGrading.setMinValue((short) 0);
        freePointGrading.setPassValue((short) 60);
        freePointGrading.setMaxValue((short) 200);
    }

    @Test
    public void shouldBeGradedTo0Test() {
        final GradedResult grade = freePointGrading.grade(0);
        Assert.assertEquals((short)0, grade.getGrade(), 0.1);
    }

    @Test
    public void shouldBeGradedTo100Test() {
        final GradedResult grade = freePointGrading.grade(50);
        Assert.assertEquals((short)100, grade.getGrade(), 0.1);
    }

    @Test
    public void shouldBeGradedTo200Test() {
        final GradedResult grade = freePointGrading.grade(100);
        Assert.assertEquals((short)200, grade.getGrade(), 0.1);
    }

    @Test
    public void shouldBeGradedTo151Test() {
        final GradedResult grade = freePointGrading.grade(75.5);
        Assert.assertEquals((short)151, grade.getGrade(), 0.1);
    }

    @Test
    public void shouldBeGradedTo187Test() {
        final GradedResult grade = freePointGrading.grade(93.3333333333);
        Assert.assertEquals((short)187, grade.getGrade(), 0.1);
    }

}
