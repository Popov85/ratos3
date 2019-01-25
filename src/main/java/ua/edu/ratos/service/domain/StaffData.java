package ua.edu.ratos.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * For security purposes to check the possibility to modify (update and delete) protected elements
 * such as themes, schemes, courses, etc.
 */
@Getter
@Setter
@AllArgsConstructor
public class StaffData {
    private Long staffId;
    private Long depId;
}
