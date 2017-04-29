package ua.zp.zsmu.ratos.learning_session.service.dto;

import com.sun.istack.internal.NotNull;

/**
 * Created by Andrey on 28.04.2017.
 */
public final class SchemeDTO {

        private final Long id;
        private final String scheme;
        private final String mask;

        public SchemeDTO(@NotNull final Long id, @NotNull final String scheme, @NotNull final String mask) {
                this.id = id;
                this.scheme = scheme;
                this.mask = mask;
        }
}
