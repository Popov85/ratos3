package ua.zp.zsmu.ratos.learning_session.service.dto;

import lombok.Data;
import lombok.Getter;
import ua.zp.zsmu.ratos.learning_session.service.Student;

import java.io.Serializable;

/**
 * Created by Andrey on 4/23/2017.
 */
@Data
@Getter
public final class ResultDTO implements Serializable {

        private static final long serialVersionUID = 4514721169327266085L;
        private final Long sid;
        private final Student student;
        private final String scheme;
        private final double result;
        private final String mark;

        public ResultDTO(Long sid, Student student, String scheme, double result, String mark) {
                this.sid = sid;
                this.student = student;
                this.scheme = scheme;
                this.result = result;
                this.mark = mark;
        }

}
