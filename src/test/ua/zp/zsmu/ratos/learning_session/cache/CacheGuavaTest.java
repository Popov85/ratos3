package ua.zp.zsmu.ratos.learning_session.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Before;
import org.junit.Test;
import ua.zp.zsmu.ratos.learning_session.model.Question;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 20.04.2017.
 */
public class CacheGuavaTest {

        private final LoadingCache<String, List<Question>> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<Question>>() {
                @Override
                public List<Question> load(String key) throws Exception {
                        return updateCache();
                }
        });

        private final Cache<String, List<Question>> myCache =CacheBuilder.newBuilder()
                .concurrencyLevel(4)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build();



        @Before
        public void before() {
                //new CacheGuavaTest();
        }

        private List<Question> updateCache() {
                System.out.println("update is called...");
                List<Question> list = new ArrayList<>();
                Question q1 = new Question();
                q1.setId(1001L);
                q1.setTitle("Q1_original");
                q1.setLevel((short) 1);

                Question q2 = new Question();
                q1.setId(1002L);
                q1.setTitle("Q2_original");
                q1.setLevel((short) 1);

                Question q3 = new Question();
                q1.setId(1003L);
                q1.setTitle("Q3_original");
                q1.setLevel((short) 3);

                list.add(q1);
                list.add(q2);
                list.add(q3);
                return  list;
        }

        @Test
        public void emptyCache() {
                //cache.put("s1", new ArrayList<>());
                System.out.println("cache: "+cache.toString());
                System.out.println("cache key s1: "+cache.getUnchecked("s1"));

                MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
                long usedMemory = heapUsage.getUsed() / 1000000;
                long maxMemory = heapUsage.getMax() / 1000000;
                System.out.println("Memory Used :" + usedMemory + "Mb / max: " + maxMemory + "Mb");
        }
}

class UserTest {
        private String name;
        private List<Question> questions;

        public UserTest(String name, List<Question> questions) {
                this.name = name;
                this.questions = questions;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public List<Question> getQuestions() {
                return questions;
        }

        public void setQuestions(List<Question> questions) {
                this.questions = questions;
        }

        @Override
        public String toString() {
                return "UserTest{" +
                        "name='" + name + '\'' +
                        ", questions=" + questions +
                        '}';
        }
}