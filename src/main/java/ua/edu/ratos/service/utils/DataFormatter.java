package ua.edu.ratos.service.utils;

import lombok.experimental.UtilityClass;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@UtilityClass
public class DataFormatter {

    public String getPrettyDouble(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String result = df.format(value);
        // If fraction is 0, let's omit it in the resulting value
        String[] split = result.split(".");
        if (split.length>1 && split[1] == "0") return split[0];
        return result;
    }

}
