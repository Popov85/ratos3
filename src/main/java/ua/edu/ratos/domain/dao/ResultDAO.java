package ua.edu.ratos.domain.dao;

import org.springframework.stereotype.Component;
import ua.edu.ratos.domain.model.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ResultDAO {

    private static List<Result> results = Arrays.asList(
            new Result(1L, "John", 100),
            new Result(2L, "Mari", 90),
            new Result(3L, "Jack", 50)
    );

    public Result findOne(long id) {
        Optional<Result> matchingObject = results.stream().
                filter(p -> p.getResultId()==id).
                findFirst();
        return matchingObject.get();
    }

    public List<Result> findAll() {
        return results;
    }
}
