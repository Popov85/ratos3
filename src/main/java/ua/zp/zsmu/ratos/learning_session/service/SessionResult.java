package ua.zp.zsmu.ratos.learning_session.service;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andrey on 19.04.2017.
 */
@Data
@Getter
public final class SessionResult implements Serializable {

        private static final long serialVersionUID = 5901899487530025297L;

        private final double result;
        private final Student student;
        private final Scheme scheme;
        private final List<ThemeResult> themeResults;

        public SessionResult(final double result, @NotNull final Student student, @NotNull final Scheme scheme,
                             @NotNull final List<ThemeResult> themeResults) {
                this.result = result;
                this.student = student;
                this.scheme = scheme;
                this.themeResults = themeResults;
        }
}
