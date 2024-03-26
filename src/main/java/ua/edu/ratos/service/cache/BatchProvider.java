package ua.edu.ratos.service.cache;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.NamedService;

public interface BatchProvider extends NamedService<String> {

    int BATCH_SIZE = 20;

    Slice<Scheme> getBatch(Pageable pageable, Object... params);

    default Pageable getPageable() {
        Sort sort = Sort.by(Sort.Direction.ASC, "schemeId");
        return PageRequest.of(0, BATCH_SIZE, sort);
    }

}
