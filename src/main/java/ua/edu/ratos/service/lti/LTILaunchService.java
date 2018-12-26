package ua.edu.ratos.service.lti;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LTILaunchService {

    @Value("${ratos.lti.1p0.properties.launch.url}")
    private String baseLaunchUrl;

    /**
     * This URL is prepared to be inserted by an instructor directly to LMS settings
     * @param schemeId
     * @return URL of LMS-launch request
     */
    public String getLaunchURL(Long schemeId) {
        return baseLaunchUrl+"?schemeId="+schemeId;
    }
}
