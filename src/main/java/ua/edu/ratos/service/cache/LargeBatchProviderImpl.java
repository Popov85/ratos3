package ua.edu.ratos.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ua.edu.ratos.dao.entity.Scheme;
import ua.edu.ratos.service.SchemeService;

@Service
class LargeBatchProviderImpl implements BatchProvider {

    private final SchemeService schemeService;

    @Autowired
    LargeBatchProviderImpl(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @Override
    public Slice<Scheme> getBatch(Pageable pageable, Object... objects) {
        return schemeService.findLargeForCachedSession(pageable);
    }

    @Override
    public String type() {
        return "large";
    }

}
