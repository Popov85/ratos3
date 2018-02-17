package ua.zp.zsmu.ratos.learning_session.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Andrey on 06.06.2017.
 */
@Controller
public class ResultController {

        @GetMapping("/ratos/results12")
        public String results12() {
                return "results12";
        }
}
