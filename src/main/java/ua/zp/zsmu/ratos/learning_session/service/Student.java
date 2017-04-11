package ua.zp.zsmu.ratos.learning_session.service;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
public class Student implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;
        private String surname;
        private String faculty;
        private String yearOfStudy;
        private String className;

        public Student() {
                this.name = "Default name";
                this.surname = "Default surname";
                this.faculty = "Default faculty";
                this.yearOfStudy="0";
                this.className = "1";
        }
}
