package ua.edu.ratos.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;

@Service
class CourseBatchProviderImpl implements BatchProvider {

    private final SchemeService schemeService;

    @Autowired
    CourseBatchProviderImpl(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Override
    public Slice<Scheme> getBatch(Pageable pageable, Object... objects) {
        Long courseId = (Long) objects[0];
        return schemeService.findCoursesSchemesForCachedSession(pageable, courseId);
    }

    @Override
    public String type() {
        return "course";
    }

}
