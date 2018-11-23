package ua.edu.ratos.service.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Optional;

@AllArgsConstructor
public class ComplaintInDto {
    /**
     * There are the following types of complaints supported:
     * (1)Incorrect statement of question
     * (2)Typo in question
     * (3)Typo in an answerIds
     * (4)Bad question formatting
     * (5)Bad answer formatting
     */
    @Min(1)
    @Max(5)
    @Getter
    private int type;

    @Size(max = 1000, message = "{dto.string.invalid}")
    private String complaint;

    public Optional<String> getComplaint() {
        return Optional.of(complaint);
    }
}
