package ua.edu.ratos.service.dto.view;

import lombok.*;
import lombok.experimental.Accessors;


@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TypeOutDto {
    private Long typeId;
    private String type;
    private short l1;
    private short l2;
    private short l3;
    private int questions;
}
