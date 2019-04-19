package ua.edu.ratos.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;

@Service
class DepartmentBatchProviderImpl implements BatchProvider {

    private final SchemeService schemeService;

    @Autowired
    DepartmentBatchProviderImpl(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Override
    public Slice<Scheme> getBatch(Pageable pageable, Object... params) {
        Long depId = (Long) params[0];
        return schemeService.findDepartmentSchemesForCachedSession(pageable, depId);
    }

    @Override
    public String type() {
        return "department";
    }

}
