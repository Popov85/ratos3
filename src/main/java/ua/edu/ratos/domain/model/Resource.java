package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Resource {
    private long resourceId;
    private String resourceLink;
    private String description;
}
