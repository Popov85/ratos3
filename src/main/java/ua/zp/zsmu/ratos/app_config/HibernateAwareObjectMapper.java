package ua.zp.zsmu.ratos.app_config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Created by Andrey on 4/5/2017.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

        public HibernateAwareObjectMapper() {
                registerModule(new Hibernate5Module());
        }
}
