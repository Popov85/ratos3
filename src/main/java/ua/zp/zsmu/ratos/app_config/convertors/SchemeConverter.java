package ua.zp.zsmu.ratos.app_config.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ua.zp.zsmu.ratos.learning_session.model.Scheme;
import ua.zp.zsmu.ratos.learning_session.service.SchemeService;

/**
 * Created by Andrey on 5/2/2017.
 */
public class SchemeConverter implements Converter<String, Scheme> {

        @Autowired
        private SchemeService schemeService;

        @Override
        public Scheme convert(String id) {
                return this.schemeService.findOneWithThemes(Long.parseLong(id));
        }
}
