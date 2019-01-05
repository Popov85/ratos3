package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartData {
    private final String key;
    private final Long schemeId;
    private final Long userId;
    private Long lmsId;

    public StartData(String key, Long schemeId, Long userId) {
        this.key = key;
        this.schemeId = schemeId;
        this.userId = userId;
    }

    public StartData(String key, Long schemeId, Long userId, Long lmsId) {
        this.key = key;
        this.schemeId = schemeId;
        this.userId = userId;
        this.lmsId = lmsId;
    }
}
