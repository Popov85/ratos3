package ua.zp.zsmu.ratos.learning_session.service.dto;

import lombok.Data;
import lombok.Getter;

/**
 * Created by Andrey on 4/23/2017.
 */
@Data
@Getter
public final class AnswerDTO {
        private final Long aid;
        private final String title;

        public AnswerDTO(Long aid, String title) {
                this.aid = aid;
                this.title = title;
        }
}
