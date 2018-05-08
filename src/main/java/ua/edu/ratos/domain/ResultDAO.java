package ua.edu.ratos.domain;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ResultDAO {

    private static List<Result> results = Arrays.asList(
            new Result(1, "John", 100),
            new Result(2, "Mari", 90),
            new Result(3, "Jack", 50)
    );

    public Result findOne(long id) {
        Optional<Result> matchingObject = results.stream().
                filter(p -> p.getId()==id).
                findFirst();
        return matchingObject.get();
    }

    public List<Result> findAll() {
        return results;
    }
}
