package ua.edu.ratos.service.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Profile({"dev", "demo"})
public class RealWordGenerator {

    private static final String TEXT = "lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua ut enim ad minim veniam quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur excepteur sint occaecat cupidatat non proident sunt in culpa qui officia deserunt mollit anim id est laborum" +
            "curabitur pretium tincidunt lacus nulla gravida orci a odio nullam varius turpis et commodo pharetra est eros bibendum elit nec luctus magna felis sollicitudin mauris integer in mauris eu nibh euismod gravida duis ac tellus et risus vulputate vehicula donec lobortis risus a elit etiam tempor ut ullamcorper ligula eu tempor congue eros est euismod turpis id tincidunt sapien risus a quam maecenas fermentum consequat mi donec fermentum pellentesque malesuada" +
            " nulla a mi duis sapien sem aliquet nec commodo eget consequat quis neque aliquam faucibus elit ut dictum aliquet felis nisl adipiscing sapien sed malesuada diam lacus eget erat cras mollis scelerisque nunc nullam arcu aliquam consequat curabitur augue lorem dapibus quis laoreet et pretium ac nisi aenean magna nisl mollis quis molestie eu feugiat in orci in hac habitasse platea dictumst";

    private static List<String> WORDS = Arrays.asList(TEXT.split("\\s+"));

    @Autowired
    private Rnd rnd;


    public String createString() {
        int index = rnd.rnd(0, WORDS.size());
        return WORDS.get(index);
    }

    public String createSentence(int minWords, int maxWords, boolean isQuestion) {
        int words = rnd.rnd(minWords, maxWords + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words; i++) {
            String string = createString();
            if (i == 0) {
                string = string.substring(0, 1).toUpperCase() + string.substring(1);
            }
            sb.append(string);
            sb.append(" ");
        }
        if (isQuestion) {
            sb.append("?");
        } else {
            sb.append(".");
        }
        String text = sb.toString();
        //log.debug("Created text of words = {}, length = {} symbols", words, text.length());
        return text;
    }

    /*public static void main(String[] args) {
        RealWordGenerator rwg = new RealWordGenerator();
        //System.out.println(SENTENCES.size());
        String text = rwg.createSentence(15, 30, true);
        System.out.println(text);
    }*/

}
