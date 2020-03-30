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
public class RealSentenceGenerator {
    private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "+
            "Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra, est eros bibendum elit, nec luctus magna felis sollicitudin mauris. Integer in mauris eu nibh euismod gravida. Duis ac tellus et risus vulputate vehicula. Donec lobortis risus a elit. Etiam tempor. Ut ullamcorper, ligula eu tempor congue, eros est euismod turpis, id tincidunt sapien risus a quam. Maecenas fermentum consequat mi. Donec fermentum. Pellentesque malesuada " +
            "nulla a mi. Duis sapien sem, aliquet nec, commodo eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl adipiscing sapien, sed malesuada diam lacus eget erat. Cras mollis scelerisque nunc. Nullam arcu. Aliquam consequat. Curabitur augue lorem, dapibus quis, laoreet et, pretium ac, nisi. Aenean magna nisl, mollis quis, molestie eu, feugiat in, orci. In hac habitasse platea dictumst.";

    static List<String> SENTENCES = Arrays.asList(TEXT.split("\\."));

    @Autowired
    private Rnd rnd;


    public String selectSentence() {
        int index = rnd.rnd(0, SENTENCES.size());
        return SENTENCES.get(index);
    }

    public String createText(int minSentences, int maxSentences, boolean isQuestion) {
        long sentences = rnd.rnd(minSentences, maxSentences);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences; i++) {
            String string = selectSentence();
            sb.append(string.trim());
            sb.append(". ");
        }
        if (isQuestion) {
            sb.append("?");
        } else {
            sb.append(".");
        }
        String text = sb.toString();
        return text;
    }

    /*public static void main(String[] args) {
        RealSentenceGenerator rsg = new RealSentenceGenerator();
        //System.out.println(SENTENCES.size());
        String text = rsg.createText(4, 10, true);
        System.out.println(text);
    }*/
}
