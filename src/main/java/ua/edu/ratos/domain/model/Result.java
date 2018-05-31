package ua.edu.ratos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Long resultId;
    private String user;
    private int result;

    public String calculateMark() {
        if (result<0||result>100) throw new RuntimeException("Out of range number!");
        if (result>=85) return "Excellent";
        if (result>=70&&result<85) return "Good";
        if (result>=50&&result<70) return "Satisfactory";
        return "Unsatisfactory";
    }

}
