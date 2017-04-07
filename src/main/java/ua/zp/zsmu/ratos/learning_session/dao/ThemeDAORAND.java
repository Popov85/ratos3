package ua.zp.zsmu.ratos.learning_session.dao;

import ua.zp.zsmu.ratos.learning_session.model.Question;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Andrey on 07.04.2017.
 */
public class ThemeDAORAND {

        @PersistenceContext
        private EntityManager em;

        public List<Question> findOneThemeWithNRandomQuestions(Long id, Integer quantity) {
                String query = "SELECT t FROM Theme t LEFT JOIN FETCH t.questions WHERE t.id=:id";
                return em.createQuery(query)
                        .setParameter("id", id)
                        .setMaxResults(quantity)
                        .getResultList();
        }
}
