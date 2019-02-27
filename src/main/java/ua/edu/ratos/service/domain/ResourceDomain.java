package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDomain {

    private Long resourceId;

    private String link;

    private String description;
}
