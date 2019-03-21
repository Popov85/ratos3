package ua.edu.ratos.service.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;

import java.util.Map;

@Service
public class GameLabelResolver {

    private AppProperties appProperties;

    @Autowired
    public void setAppProperties(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * Calculates the current user's title (label) based on his current achievements.
     * @param totalWins how many weeks the user has won so far
     * @return user's title or label
     */
    public String getLabel(int totalWins) {
        AppProperties.Game props = appProperties.getGame();
        Map<Integer, String> label = props.getUserLabel();
        if (label.get(totalWins)!=null) return label.get(totalWins);
        int previousKey = 0;
        for (Integer key : label.keySet()) {
            if (key > totalWins) return label.get(previousKey);
            previousKey = key;
        }
        return label.get(previousKey);
    }
}
