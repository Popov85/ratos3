package ua.edu.ratos.domain.repository;

import org.springframework.stereotype.Repository;
import ua.edu.ratos.domain.model.ResultMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ResultDao {

    private static List<ResultMock> results = Arrays.asList(
            new ResultMock(1L, "John", 100),
            new ResultMock(2L, "Mari", 90),
            new ResultMock(3L, "Jack", 50)
    );

    public ResultMock findOne(long id) {
        Optional<ResultMock> matchingObject = results.stream().
                filter(p -> p.getResultId()==id).
                findFirst();
        return matchingObject.get();
    }

    public List<ResultMock> findAll() {
        return results;
    }
}
