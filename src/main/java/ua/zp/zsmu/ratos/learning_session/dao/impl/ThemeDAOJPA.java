package ua.zp.zsmu.ratos.learning_session.dao.impl;

import org.springframework.stereotype.Repository;
import ua.zp.zsmu.ratos.learning_session.model.Theme;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Andrey on 04.04.2017.
 */
@Repository
public class ThemeDAOJPA {

        @PersistenceContext
        private EntityManager entityManager;

        public Theme findAllQuestions(Long id){
                return entityManager.find(Theme.class, id);
        }
}
