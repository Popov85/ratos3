package ua.edu.ratos.service.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long resourceId;

    private String link;

    private String description;
}
