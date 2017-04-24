package ua.zp.zsmu.ratos.learning_session.service.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.Student;
import ua.zp.zsmu.ratos.learning_session.service.ThemeResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 19.04.2017.
 */
@Data
@Getter
@AllArgsConstructor
public final class DetailedReportDTO implements Serializable {

        private static final long serialVersionUID = -3904168910755824252L;

        private final Long sid;
        private final double result;
        private final Student student;
        private final Scheme scheme;
        private final String mark;
        private final List<ThemeResult> themeResults;
}
