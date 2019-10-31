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
public class StringGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    @Autowired
    private Rnd rnd;

    public String createPrefix() {
        List<Character> letters = Arrays.asList('a','b','c','d','e','f','g');
        return Character.toString(letters.get(rnd.rnd(0, 7)))
                +Character.toString(letters.get(rnd.rnd(0, 7)))
                +Character.toString(letters.get(rnd.rnd(0, 7)));
    }

    public String createString(int min, int max) {
        int length = rnd.rnd(min, max+1);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = this.rnd.rnd(0, ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public String createText(int minWords, int maxWords, int minSymbols, int maxSymbols, boolean isQuestion) {
        int words = rnd.rnd(minWords, maxWords+1);
        StringBuilder sb = new StringBuilder(words*minSymbols);
        for (int i = 0; i < words; i++) {
            sb.append(createString(minSymbols, maxSymbols));
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

}
