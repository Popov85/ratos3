package ua.zp.zsmu.ratos.learning_session.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andrey on 4/8/2017.
 */
@Data
@Getter
//@Entity
//@Table(name="results")
public class Result {
        private String result;
        public Result(String result) {
                this.result = result;
        }

}
