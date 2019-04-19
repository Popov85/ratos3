package ua.edu.ratos.service.cache;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import ua.edu.ratos.dao.entity.Scheme;

public interface BatchProvider {

    int BATCH_SIZE = 20;

    Slice<Scheme> getBatch(Pageable pageable, Object... params);

    String type();

    default Pageable getPageable() {
        Sort sort = new Sort(Sort.Direction.ASC, "schemeId");
        return PageRequest.of(0, BATCH_SIZE, sort);
    }

}
