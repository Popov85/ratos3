package ua.zp.zsmu.ratos.learning_session.service.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andrey on 28.04.2017.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public final class SchemeDTO {

        private Long id;
        private String scheme;
        private String mask;
}
