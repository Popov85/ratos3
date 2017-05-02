package ua.zp.zsmu.ratos.learning_session.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
@Getter
@AllArgsConstructor
public class Student implements Serializable {

        private static final long serialVersionUID = -1045670516335576977L;

        private final String name;
        private final String surname;
        private final String faculty;
        private final String course;
        private final String group;

        public Student() {
                this.name = "Default name";
                this.surname = "Default surname";
                this.faculty = "Default faculty";
                this.course ="0";
                this.group = "1";
        }
}
