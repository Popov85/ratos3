package ua.edu.ratos.service.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Rnd {

    public int rnd(int from, int to) {
        Random r = new Random();
        return r.ints(from, to).findFirst().getAsInt();
    }

    public long rndOne(int bound) {
        Random r = new Random();
        return r.ints(1, bound).findFirst().getAsInt();
    }
}
