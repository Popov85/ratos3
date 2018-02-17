package ua.zp.zsmu.ratos.learning_session.service.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ua.zp.zsmu.ratos.learning_session.model.Question;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON question file sample
     [{
         "question": "Question1...",
         "difficulty": 1,
         "help": "help11...",
         "answers": [{
             "answer": "Answer11...",
             "correct": 50,
             "required": false
             },
             {
             "answer": "Answer12...",
             "correct": 50,
             "required": false
             },{
             "answer": "Answer13...",
             "correct": 0,
             "required": false
             }
        ]
     },
     {
         "question": "Question2...",
         "difficulty": 3,
         "help": "help21...",
         "answers": [{
             "answer": "Answer11...",
             "correct": 50,
             "required": false
             },
             {
             "answer": "Answer12...",
             "correct": 50,
             "required": false
             },{
             "answer": "Answer13...",
             "correct": 0,
             "required": false
             }
        ]
     }
     ]
 *
 *
 *
 * Created by Andrey on 2/3/2018.
 */
public class LineReaderJSON implements LineReader {

    @Override
    public void readLine(String line) {

    }

    @Override
    public ParsingResult<Question, Issue> getParsingResult() {
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonInput;
        List<Question> result = null;
        List<Question> list = null; //mapper.readValue(jsonInput, new TypeReference<List<Question>>(){});
        // //mapper.readValue(jsonInput, mapper.getTypeFactory().constructCollectionType(List.class, Question.class));
        try {
            list = null;
            jsonInput = Files.readAllBytes(Paths.get("D:\\questions.json"));
            ObjectReader objectReader = mapper.reader().forType(new TypeReference<List<Question>>(){});
            result = objectReader.readValue(jsonInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ParsingResult<>(result, new ArrayList<>());

    }


}
