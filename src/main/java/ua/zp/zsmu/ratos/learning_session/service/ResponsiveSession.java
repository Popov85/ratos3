package ua.zp.zsmu.ratos.learning_session.service;

import ua.zp.zsmu.ratos.learning_session.model.Answer;

/**
 * Created by Andrey on 4/8/2017.
 */
public interface ResponsiveSession extends ISession {

        Answer provideRightAnswer();
}
