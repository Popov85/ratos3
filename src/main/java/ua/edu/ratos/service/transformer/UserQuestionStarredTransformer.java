package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.UserQuestionStarred;
import ua.edu.ratos.service.dto.session.question.QuestionSessionMinOutDto;
import ua.edu.ratos.service.dto.session.question.QuestionSessionOutDto;


public interface UserQuestionStarredTransformer {

    QuestionSessionMinOutDto toDto(UserQuestionStarred entity);

    QuestionSessionOutDto toDtoExt(UserQuestionStarred entity);
}
