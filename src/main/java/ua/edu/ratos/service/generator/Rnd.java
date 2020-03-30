package ua.edu.ratos.service.generator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Profile({"dev", "demo"})
public class Rnd {

    public int rnd(int from, int to) {
        Random r = new Random();
        return r.ints(from, to).findFirst().getAsInt();
    }

    public long rndOne(int bound) {
        Random r = new Random();
        return r.ints(1, bound).findFirst().getAsInt();
    }

    public long rndOneZero(int bound) {
        Random r = new Random();
        return r.ints(0, bound).findFirst().getAsInt();
    }
}
