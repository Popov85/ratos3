package ua.zp.zsmu.ratos.learning_session.cache;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.zp.zsmu.ratos.learning_session.dao.PersistenceContext;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.model.Theme;
import ua.zp.zsmu.ratos.learning_session.service.SchemeService;
import ua.zp.zsmu.ratos.learning_session.service.cache.CacheGuava;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 25.04.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes ={PersistenceContext.class})
public class CacheGuavaTest {

        @Autowired
        private CacheGuava cacheGuava;

        @Autowired
        private SchemeService schemeService;

        @Test
        public void itShouldLoadFromDBToCacheSmoothly() {
                Scheme scheme = schemeService.findOne(53L);
                Map<Theme, Map<Integer, List<Question>>> cachedContent = cacheGuava.getCachedScheme(scheme);
                Assert.assertNotNull(cachedContent);
                System.out.println("Scheme #53 "+cachedContent);
                /*for (Theme theme : cachedContent.keySet()) {
                        System.out.println(theme.toString());
                }*/
        }
}
