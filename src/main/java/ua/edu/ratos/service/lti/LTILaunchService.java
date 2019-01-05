package ua.edu.ratos.service.lti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.config.properties.AppProperties;

@Component
public class LTILaunchService {

    private final AppProperties appProperties;

    @Autowired
    public LTILaunchService(final AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * This URL is prepared to be inserted by an instructor directly to LMS settingsDomain
     * @param schemeId
     * @return URL of LMS-launch request
     */
    public String getLaunchURL(Long schemeId) {
        return appProperties.getLti().getLaunch_url()+"?schemeId="+schemeId;
    }
}
