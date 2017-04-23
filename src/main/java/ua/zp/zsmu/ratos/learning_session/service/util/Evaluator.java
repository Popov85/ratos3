package ua.zp.zsmu.ratos.learning_session.service.util;

import ua.zp.zsmu.ratos.learning_session.model.Scheme;

/**
 * Created by Andrey on 4/23/2017.
 */
public class Evaluator {

        private Evaluator() {}

        public static String calculateMark(Scheme scheme, double result) {
                int grade3 = scheme.getGrade3StartsFrom();
                int grade4 = scheme.getGrade4StartsFrom();
                int grade5 = scheme.getGrade3StartsFrom();
                if (result<grade3) return "2";
                if (result>=grade3&&result<grade4) return "3";
                if (result>=grade4&&result<grade5) return "4";
                return "5";
        }
}
