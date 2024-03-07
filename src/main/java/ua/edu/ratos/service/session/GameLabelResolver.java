package ua.edu.ratos.service.session;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ratos.config.properties.AppProperties;
import java.util.SortedMap;

@Service
@AllArgsConstructor
public class GameLabelResolver {

    private final AppProperties appProperties;

    /**
     * Calculates the current user's title (label) based on his current achievements.
     * @param totalWins how many weeks the user has won so far
     * @return user's title or label
     */
    public String getLabel(int totalWins) {
        SortedMap<Integer, String> label = appProperties.getGame().getUserLabel();
        if (label.get(totalWins)!=null) return label.get(totalWins);
        int previousKey = 0;
        for (Integer key : label.keySet()) {
            if (key > totalWins) return label.get(previousKey);
            previousKey = key;
        }
        return label.get(previousKey);
    }
}
