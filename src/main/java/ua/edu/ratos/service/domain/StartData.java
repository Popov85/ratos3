package ua.edu.ratos.service.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
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

    public Optional<Long> getLmsId() {
        return Optional.ofNullable(lmsId);
    }
}
