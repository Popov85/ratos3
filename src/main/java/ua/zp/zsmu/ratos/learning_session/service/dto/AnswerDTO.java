package ua.zp.zsmu.ratos.learning_session.service.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Andrey on 4/23/2017.
 */
@Data
@Getter
public final class AnswerDTO implements Serializable {

        private static final long serialVersionUID = -5944167981554440817L;

        private final Long aid;
        private final String title;

        public AnswerDTO(Long aid, String title) {
                this.aid = aid;
                this.title = title;
        }
}
