package ua.edu.ratos.service.lti;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LTILaunchService {

    @Value("${ratos.lti.launch}")
    private String baseLaunchUrl;

    public String getLaunchURL(Long schemeId) {
        return baseLaunchUrl+"?schemeId="+schemeId;
    }
}
