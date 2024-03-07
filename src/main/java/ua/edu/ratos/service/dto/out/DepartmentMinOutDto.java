package ua.edu.ratos.service.dto.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DepartmentMinOutDto {

    private Long depId;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentMinOutDto that = (DepartmentMinOutDto) o;
        return Objects.equals(depId, that.depId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(depId);
    }
}
