package ua.zp.zsmu.ratos.learning_session.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

        private static final long serialVersionUID = -1045670516335576977L;

        @NotEmpty(message = "{start.empty_name}")
        @Size(min=2, max=40, message = "{start.too_short_name}")
        private String name;

        @NotEmpty(message = "{start.empty_surname}")
        @Size(min=2, max=40, message = "{start.too_short_surname}")
        private String surname;

        @NotNull
        private String faculty;

        private String course;

        @NotEmpty
        private String group;
}
